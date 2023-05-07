package com.example.atp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atp.domain.model.Student

@Database(
    entities = [
        Student::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ATPDatabase : RoomDatabase() {
    abstract val atpDao: ATPDao

    companion object {
        const val DATABASE_NAME: String = "atpDB"
    }
}