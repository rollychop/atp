package com.example.atb.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface ATBDao {

    @Query("SELECT * FROM student")
    fun getStudents(): Flow<List<Student>>

    @Query("SELECT * FROM student WHERE barcode = :barcode")
    suspend fun getStudentByBarcode(barcode: Int): Student


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM attendance_log ORDER BY datetime(date) DESC")
    fun getAttendanceLogs(): Flow<List<AttendanceLog>>

    @Query("SELECT * FROM attendance_log WHERE barcode = :barcode ORDER BY datetime(date) DESC")
    suspend fun getAttendanceLogsByBarcode(barcode: Int): List<AttendanceLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttendance(attendanceLog: AttendanceLog)


}