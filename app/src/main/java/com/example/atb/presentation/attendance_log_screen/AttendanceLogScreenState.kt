package com.example.atb.presentation.attendance_log_screen

import android.os.Parcelable
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttendanceLogScreenState(
    val attendanceLogs: List<AttendanceLog> = emptyList(),
    val student: Student? = null
) : Parcelable
