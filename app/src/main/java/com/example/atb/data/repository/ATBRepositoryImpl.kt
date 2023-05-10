package com.example.atb.data.repository

import com.example.atb.data.data_source.ATBDao
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import com.example.atb.domain.model.StudentDetail
import com.example.atb.domain.model.StudentDto
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.streams.toList


class ATBRepositoryImpl(private val atbDao: ATBDao) : ATBRepository {
    override fun getStudents(): Flow<List<Student>> = atbDao.getStudents()

    override suspend fun getStudentByBarcode(barcode: Int): Student {
        return atbDao.getStudentByBarcode(barcode)
    }

    override suspend fun insertStudent(student: Student) {
        atbDao.insertStudent(student)
    }

    override suspend fun deleteStudent(student: Student) {
        atbDao.deleteStudent(student)
    }

    override fun getAttendanceLogs(): Flow<List<AttendanceLog>> {
        return atbDao.getAttendanceLogs()
    }

    override suspend fun getStudentDetails(barcode: Int): StudentDetail {
        return StudentDetail(
            atbDao.getStudentByBarcode(barcode),
            atbDao.getAttendanceLogsByBarcode(barcode)
        )
    }

    override suspend fun insertAttendance(attendanceLog: AttendanceLog) {
        atbDao.insertAttendance(attendanceLog)
    }

    override suspend fun getAttendanceLogsByBarcode(barcode: Int): List<AttendanceLog> {
        return atbDao.getAttendanceLogsByBarcode(barcode)
    }

    override fun getStudentDetailThroughInternet(enrollmentNumber: String): Flow<Resource<StudentDto>> {
        return callbackFlow {
            launch { send(Resource.Loading()) }
            try {
                val url =
                    URL("http://jru.saral.in/jru/sabpaisa/gettoken.jsp?flag=1&action=getenrolldetail")
                val http = withContext(Dispatchers.IO) {
                    url.openConnection()
                } as HttpURLConnection
                http.requestMethod = "POST"
                http.doOutput = true
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                val data = "txtstenrollno=${enrollmentNumber.replace("/", "%2F")}"

                val out = data.toByteArray(StandardCharsets.UTF_8)
                val stream = http.outputStream
                withContext(Dispatchers.IO) {
                    stream.write(out)
                }
                if (http.responseCode !in 200..299) {
                    send(Resource.Error("Server Error ${http.responseCode}"))
                    return@callbackFlow
                }

                BufferedReader(
                    InputStreamReader(http.inputStream)
                ).use {

                    val text = it.lines()
                        .map { line ->
                            when {
                                line.contains("id=\"txtstname\"") -> "name" to line
                                line.contains("id=\"txtstregnno\"") -> "registrationNumber" to line
                                line.contains("id=\"txtstfname\"") -> "fathersName" to line
                                line.contains("id=\"txtstenrollno\"") -> "enrollmentNumber" to line
                                line.contains("id=\"txtstcourse\"") -> "course" to line
                                else -> null
                            }
                        }
                        .filter { line -> line != null }
                        .map { line ->
                            val s = line!!.second.indexOf("value=\"")
                            val a = line.second.substring(s + 7, line.second.length)
                            val b = a.indexOfFirst { ch -> ch == '"' }
                            line.first to a.substring(0, b)
                        }
                        .toList()
                    var studentDto = StudentDto()
                    for (pair in text) {
                        studentDto = studentDto.addValue(pair)
                    }
                    if (studentDto.name.isNotEmpty())
                        send(Resource.Success(studentDto))
                    else
                        send(Resource.Error("Invalid Enrollment Number"))
                }
                http.disconnect()
            } catch (ioe: IOException) {
                send(Resource.Error(ioe.message ?: "IO ERROR"))
                ioe.printStackTrace()
            }
            awaitClose { }
        }

    }
}