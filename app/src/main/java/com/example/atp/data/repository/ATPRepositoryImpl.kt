package com.example.atp.data.repository

import com.example.atp.data.data_source.ATPDao
import com.example.atp.domain.model.Student
import com.example.atp.domain.repository.ATPRepository
import com.example.atp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ATPRepositoryImpl(private val atpDao: ATPDao) : ATPRepository {
    override fun getStudents(): Flow<Resource<List<Student>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val students = atpDao.getStudents()
                emit(Resource.Success(students))
            } catch (e: IOException) {
                emit(Resource.Error("Failed to load Student"))
            }
        }
    }

    override suspend fun getStudentByBarcode(barcode: Int): Student? {
        return atpDao.getStudentByBarcode(barcode)
    }

    override suspend fun insertStudent(student: Student) {
        atpDao.insertStudent(student)
    }

    override suspend fun deleteStudent(student: Student) {
        atpDao.deleteStudent(student)
    }
}