package com.example.atb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentDetail(
    val student: Student,
    val attendanceLogs: List<AttendanceLog>
) : Parcelable
