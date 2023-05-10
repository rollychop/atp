package com.example.atb.domain.repository

import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import com.example.atb.domain.model.StudentDetail
import com.example.atb.domain.model.StudentDto
import com.example.atb.util.Resource
import kotlinx.coroutines.flow.Flow


interface ATBRepository {
    fun getStudents(): Flow<List<Student>>
    suspend fun getStudentByBarcode(barcode: Int): Student
    suspend fun insertStudent(student: Student)
    suspend fun deleteStudent(student: Student)

    suspend fun insertAttendance(attendanceLog: AttendanceLog)
    fun getAttendanceLogs(): Flow<List<AttendanceLog>>

    suspend fun getAttendanceLogsByBarcode(barcode: Int): List<AttendanceLog>
    suspend fun getStudentDetails(barcode: Int): StudentDetail

     fun getStudentDetailThroughInternet(enrollmentNumber: String): Flow<Resource<StudentDto>>

}