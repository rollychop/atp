package com.example.atb.presentation.student_detail_screen

import android.os.Parcelable
import com.example.atb.domain.model.StudentDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentDetailScreenState(
    val studentDetail: StudentDetail? = null,
    val loading: Boolean = false,
    val error: String = ""
) : Parcelable

