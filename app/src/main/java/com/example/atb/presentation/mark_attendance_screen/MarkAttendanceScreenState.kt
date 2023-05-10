package com.example.atb.presentation.mark_attendance_screen

import android.os.Parcelable
import com.example.atb.domain.model.Student
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarkAttendanceScreenState(
    val error: String = "",
    val details: String = "Start scanning to get details",
    val loading: Boolean = false,
    val student: Student? = null,
) : Parcelable
