package com.example.atb.presentation.student_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.repository.ATBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val atbRepository: ATBRepository,
) : ViewModel() {

    private val _state = mutableStateOf(StudentScreenState())
    val state: State<StudentScreenState> = _state

    init {
        getStudents()
    }


    private fun getStudents() {
        atbRepository.getStudents().onEach { students ->
            _state.value = StudentScreenState(students = students)
        }.launchIn(viewModelScope)
    }
}