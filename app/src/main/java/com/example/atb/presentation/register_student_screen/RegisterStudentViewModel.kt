package com.example.atb.presentation.register_student_screen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atb.domain.model.Student
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.domain.repository.ScannerRepository
import com.example.atb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterStudentViewModel @Inject constructor(
    private val atbRepository: ATBRepository,
    private val scannerRepository: ScannerRepository
) : ViewModel() {

    private val _state = mutableStateOf(RegisterStudentScreenState())
    val state: State<RegisterStudentScreenState> = _state

    private fun scanBarcode() {
        scannerRepository.startScanning().onEach {
            _state.value = state.value.copy(barcode = it ?: "")
        }.launchIn(viewModelScope)
    }

    private fun getStudentDetailsFromInternet(enrollmentNumber: String) {
        val (isError, msg) = checkEnrollmentNumber()
        _state.value =
            state.value.copy(
                textFieldErrorState = state.value.textFieldErrorState.copy(
                    enrollNum = msg
                )
            )
        if (isError) return

        atbRepository.getStudentDetailThroughInternet(enrollmentNumber).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = resource.message ?: "Error fetching detail from internet",
                        verifying = false,
                        textFieldErrorState = state.value.textFieldErrorState.copy(
                            enrollNum = resource.message?.let { if (it.contains("Enrollment")) it else "" }
                                ?: ""
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        verifying = true
                    )
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        name = resource.data?.name ?: state.value.name,
                        course = resource.data?.course ?: state.value.course,
                        enrollNum = resource.data?.enrollmentNumber ?: state.value.enrollNum,
                        verifying = false,
                        verified = true,
                        textFieldErrorState = state.value.textFieldErrorState.copy(
                            name = "",
                            enrollNum = "",
                            course = ""
                        )
                    )
                }
            }

        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onChange(action: Action) {
        when (action) {
            is Action.Barcode -> _state.value = state.value.copy(barcode = action.value)
            is Action.Course -> {
                _state.value = state.value.copy(course = action.value)
            }

            is Action.Email -> _state.value = state.value.copy(email = action.value)
            is Action.EnrollmentNumber -> {
                _state.value =
                    state.value.copy(enrollNum = action.value)
                if (state.value.verified) {
                    _state.value = state.value.copy(verified = false)
                }
            }

            is Action.Name -> {
                _state.value = state.value.copy(name = action.value)
                if (state.value.verified) {
                    _state.value = state.value.copy(verified = false)
                }
            }

            is Action.Semester -> _state.value = state.value.copy(sem = action.value)
            is Action.ClearAll -> _state.value = action.state ?: RegisterStudentScreenState()
            Action.Submit -> {
                if (!hasErrorInTextFields()) {
                    register(
                        Student(
                            name = state.value.name,
                            course = state.value.course,
                            enrollNumber = state.value.enrollNum,
                            barcode = state.value.barcode.toInt(),
                            sem = state.value.sem.toInt(),
                            email = state.value.email,
                        )
                    )
                }
            }

            is Action.Verify -> {
                getStudentDetailsFromInternet(action.enrollmentNumber)
            }

            Action.Scan -> scanBarcode()
        }
    }

    private fun checkName(): Pair<Boolean, String> {
        return if (state.value.name.isEmpty()) {
            true to "Enter Valid Name"
        } else if (state.value.name.length < 4) {
            true to "Enter Valid Name"
        } else
            return false to ""
    }

    private fun checkEnrollmentNumber(): Pair<Boolean, String> {
        return if (state.value.enrollNum.isEmpty()) {
            true to "Enrollment number is Empty"
        } else if (state.value.enrollNum.count { it == '/' } < 2) {
            true to "Invalid Enrollment Number"
        } else
            return false to ""
    }

    private fun hasErrorInTextFields(): Boolean {
        var isError = false
        val name = checkName()
        if (name.first) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(name = name.second))
            isError = true
        }
        val enrollmentNumber = checkEnrollmentNumber()
        if (enrollmentNumber.first) {
            _state.value =
                state.value.copy(
                    textFieldErrorState = state.value.textFieldErrorState.copy(
                        enrollNum = enrollmentNumber.second
                    )
                )
            isError = true
        }
        if (state.value.barcode.isEmpty()) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(barcode = "Enter Valid Barcode Value"))
            isError = true
        } else if (state.value.barcode.isDigitsOnly().not()) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(barcode = "Barcode must be a number"))
            isError = true
        }
        if (state.value.course.isEmpty()) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(course = "Course is Empty"))
            isError = true
        }
        if (state.value.sem.isEmpty()) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(sem = "Semester is Empty"))
            isError = true
        } else if (state.value.sem.isDigitsOnly().not() || state.value.sem.toInt() !in 1..8) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(sem = "Enter Valid Semester"))
            isError = true
        }
        if (Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches().not()) {
            _state.value =
                state.value.copy(textFieldErrorState = state.value.textFieldErrorState.copy(email = "Enter Valid Email"))
            isError = true
        }
        return isError

    }

    private fun register(student: Student) {
        viewModelScope.launch {
            atbRepository.insertStudent(student)
            _state.value = state.value.copy(success = true)
        }
    }


}

sealed class Action(val value: String) {
    data class Name(val name: String) : Action(name)
    data class Email(val email: String) : Action(email)
    data class Course(val course: String) : Action(course)
    data class EnrollmentNumber(val enrollmentNumber: String) : Action(enrollmentNumber)
    data class Barcode(val barcode: String) : Action(barcode)
    data class Semester(val semester: String) : Action(semester)
    data class ClearAll(val state: RegisterStudentScreenState? = null) : Action("")
    object Submit : Action("")
    data class Verify(val enrollmentNumber: String) : Action(enrollmentNumber)
    object Scan : Action("")
}