package com.example.atb.presentation.attendance_log_screen

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
class AttendanceLogViewModel @Inject constructor(
    private val atbRepository: ATBRepository
) : ViewModel() {

    private val _state = mutableStateOf(AttendanceLogScreenState())
    val state: State<AttendanceLogScreenState> = _state

    init {
        getAttendanceLogs()
    }

    private fun getAttendanceLogs() {
        atbRepository.getAttendanceLogs().onEach { logs ->
            _state.value = AttendanceLogScreenState(
                logs
            )
        }.launchIn(viewModelScope)
    }
}