package com.example.atb.data.repository

import com.example.atb.data.data_source.ATBDao
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import com.example.atb.domain.model.StudentDetail
import com.example.atb.domain.repository.ATBRepository
import kotlinx.coroutines.flow.Flow

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
}