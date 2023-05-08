package com.example.atb.presentation.student_screen

import android.os.Parcelable
import com.example.atb.domain.model.Student
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val students: List<Student> = emptyList()
) : Parcelable {
}