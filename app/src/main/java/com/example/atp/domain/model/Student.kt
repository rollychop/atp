package com.example.atp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "student"
)
data class Student(
    val name: String,
    val enrollNumber: String,
    val course: String,
    val sem: Int,
    val email: String,
    @PrimaryKey(autoGenerate = false)
    val barcode: Int
)
