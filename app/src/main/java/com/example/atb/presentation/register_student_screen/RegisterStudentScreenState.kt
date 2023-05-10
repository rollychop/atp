package com.example.atb.presentation.register_student_screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterStudentScreenState(
    val loading: Boolean = false,
    val error: String = "",
    val name: String = "",
    val enrollNum: String = "",
    val barcode: String = "",
    val course: String = "",
    val sem: String = "",
    val email: String = "",
    val verified: Boolean = false,
    val verifying: Boolean = false,
    val textFieldErrorState: TextFieldErrorState = TextFieldErrorState(),
    val success: Boolean = false,

    ) : Parcelable

@Parcelize
data class TextFieldErrorState(
    val name: String = "",
    val enrollNum: String = "",
    val barcode: String = "",
    val course: String = "",
    val sem: String = "",
    val email: String = "",
) : Parcelable
