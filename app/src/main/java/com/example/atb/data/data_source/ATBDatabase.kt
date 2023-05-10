package com.example.atb.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.atb.data.util.AtbTypeConverter
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student

@Database(
    entities = [
        Student::class,
        AttendanceLog::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(AtbTypeConverter::class)
abstract class ATBDatabase : RoomDatabase() {
    abstract val atbDao: ATBDao

    companion object {
        const val DATABASE_NAME: String = "atpDB"
    }
}