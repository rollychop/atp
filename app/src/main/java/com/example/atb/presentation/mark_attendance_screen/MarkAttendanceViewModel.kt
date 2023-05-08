package com.example.atb.presentation.mark_attendance_screen

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.repository.ATBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MarkAttendanceViewModel @Inject constructor(
    private val atbRepository: ATBRepository
) : ViewModel() {

    private val _state = mutableStateOf(MarkAttendanceScreenState())
    val state: State<MarkAttendanceScreenState> = _state

    fun mark(attendanceLog: AttendanceLog) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                atbRepository.insertAttendance(attendanceLog)
                _state.value = MarkAttendanceScreenState()
            } catch (e: SQLiteConstraintException) {
                _state.value =
                    MarkAttendanceScreenState(error = "${attendanceLog.barcode} is invalid first register then mark attendance :(")
            }
        }
    }
}