package com.example.atb.presentation.mark_attendance_screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarkAttendanceScreenState(
    val error: String = ""
) : Parcelable
