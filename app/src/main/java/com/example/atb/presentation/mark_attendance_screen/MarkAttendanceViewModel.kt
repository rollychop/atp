package com.example.atb.presentation.mark_attendance_screen

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.domain.repository.ScannerRepository
import com.example.atb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MarkAttendanceViewModel @Inject constructor(
    private val atbRepository: ATBRepository,
    private val scannerRepository: ScannerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MarkAttendanceScreenState())
    val state: StateFlow<MarkAttendanceScreenState> = _state.asStateFlow()

    fun startScanningAndMarkAttendance() {
        scannerRepository.startScanningAndMarkAttendance().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        MarkAttendanceScreenState(error = result.message ?: "Unexpected Error")
                }

                is Resource.Loading -> {
                    _state.value = MarkAttendanceScreenState(loading = true)
                }

                is Resource.Success -> {
                    _state.value = MarkAttendanceScreenState(student = result.data)

                }
            }
        }.launchIn(viewModelScope)
    }

    fun mark(attendanceLog: AttendanceLog) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                atbRepository.insertAttendance(attendanceLog)
                val student = atbRepository.getStudentByBarcode(attendanceLog.barcode)
                _state.value = MarkAttendanceScreenState(student = student)
            } catch (e: SQLiteConstraintException) {
                _state.value =
                    MarkAttendanceScreenState(error = "${attendanceLog.barcode} is invalid first register then mark attendance :(")
            }
        }
    }
}