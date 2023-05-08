package com.example.atb.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "student"
)
@Parcelize
data class Student(
    val name: String,
    val enrollNumber: String,
    val course: String,
    val sem: Int,
    val email: String,
    @PrimaryKey(autoGenerate = false)
    val barcode: Int
) : Parcelable
