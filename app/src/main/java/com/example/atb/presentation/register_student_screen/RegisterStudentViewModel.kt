package com.example.atb.presentation.register_student_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.model.Student
import com.example.atb.domain.repository.ATBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterStudentViewModel @Inject constructor(
    private val atbRepository: ATBRepository
) : ViewModel() {

    fun register(student: Student) {
        viewModelScope.launch {
            atbRepository.insertStudent(student)
        }
    }


}