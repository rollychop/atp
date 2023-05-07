package com.example.atp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atp.domain.model.Student

@Dao
interface ATPDao {

    @Query("SELECT * FROM student")
    suspend fun getStudents(): List<Student>

    @Query("SELECT * FROM student WHERE barcode = :barcode")
    suspend fun getStudentByBarcode(barcode: Int): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)
}