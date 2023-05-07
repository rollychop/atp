package com.example.atp.domain.repository

import com.example.atp.domain.model.Student
import com.example.atp.util.Resource
import kotlinx.coroutines.flow.Flow


interface ATPRepository {
    fun getStudents(): Flow<Resource<List<Student>>>
    suspend fun getStudentByBarcode(barcode: Int): Student?
    suspend fun insertStudent(student: Student)
    suspend fun deleteStudent(student: Student)

}