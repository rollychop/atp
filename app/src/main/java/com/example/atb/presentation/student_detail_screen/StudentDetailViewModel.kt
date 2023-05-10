package com.example.atb.presentation.student_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.model.Student
import com.example.atb.domain.model.StudentDetail
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val atbRepository: ATBRepository
) : ViewModel() {
    private val _state = mutableStateOf(StudentDetailScreenState())
    val state: State<StudentDetailScreenState> = _state

    init {
        getStudentDetails()
    }

    private fun getStudentDetails() {
        val student: Student = savedStateHandle.navArgs()
        viewModelScope.launch {
            val logs = atbRepository.getAttendanceLogsByBarcode(student.barcode)
            _state.value = StudentDetailScreenState(
                studentDetail = StudentDetail(student, logs)
            )
        }
    }
}