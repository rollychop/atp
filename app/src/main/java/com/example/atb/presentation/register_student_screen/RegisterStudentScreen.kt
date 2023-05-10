package com.example.atb.presentation.register_student_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.presentation.component.DialogItem
import com.example.atb.presentation.util.WithTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination
fun RegisterStudentScreen(
    vm: RegisterStudentViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    RegisterSection(vm.state.value, vm::onChange, navigator::popBackStack)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterSection(
    state: RegisterStudentScreenState,
    onEvent: (Action) -> Unit,
    onBackPressed: () -> Unit
) {
    WithTopAppBar(
        onLeftIconClick = onBackPressed,
        title = "Register Student"
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                value = state.enrollNum,
                onValueChange = { inputEnrollNum ->
                    onEvent(Action.EnrollmentNumber(inputEnrollNum))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Enrollment Number")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Numbers,
                        contentDescription = "Enrollment Number"
                    )
                },
                trailingIcon = {
                    if (state.verified) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Enrollment number is Verified",
                            tint = Color.Green
                        )
                    } else if (state.verifying) {
                        CircularProgressIndicator()
                    } else {
                        TextButton(onClick = {
                            onEvent(Action.Verify(state.enrollNum))
                        }) {
                            Text(text = "Verify")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = state.textFieldErrorState.enrollNum.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.enrollNum.isNotEmpty())
                        Text(text = state.textFieldErrorState.enrollNum)
                }
            )
            OutlinedTextField(
                value = state.name,
                onValueChange = { inputName ->
                    onEvent(Action.Name(inputName))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Name")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Name"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = state.textFieldErrorState.name.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.name.isNotEmpty())
                        Text(text = state.textFieldErrorState.name)
                }


            )

            OutlinedTextField(
                value = state.course,
                onValueChange = { inputCourse ->
                    onEvent(Action.Course(inputCourse))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Course")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Subject,
                        contentDescription = "Course"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ), isError = state.textFieldErrorState.course.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.course.isNotEmpty())
                        Text(text = state.textFieldErrorState.course)
                }
            )


            OutlinedTextField(
                value = state.sem,
                onValueChange = { inputSem ->
                    onEvent(Action.Semester(inputSem))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Semester")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Assessment,
                        contentDescription = "Semester"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = state.textFieldErrorState.sem.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.sem.isNotEmpty())
                        Text(text = state.textFieldErrorState.sem)
                }
            )


            OutlinedTextField(
                value = state.email,
                onValueChange = { inputEmail ->
                    onEvent(Action.Email(inputEmail))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Email")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),

                isError = state.textFieldErrorState.email.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.email.isNotEmpty())
                        Text(text = state.textFieldErrorState.email)
                }
            )

            OutlinedTextField(
                value = state.barcode,
                onValueChange = { inputBarcode ->
                    onEvent(Action.Barcode(inputBarcode))
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Barcode")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.QrCode,
                        contentDescription = "Barcode"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { onEvent(Action.Scan) }) {
                        Icon(
                            imageVector = Icons.Rounded.QrCodeScanner,
                            contentDescription = "Qr Scanner"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ), isError = state.textFieldErrorState.barcode.isNotEmpty(),
                supportingText = {
                    if (state.textFieldErrorState.barcode.isNotEmpty())
                        Text(text = state.textFieldErrorState.barcode)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    onEvent(Action.ClearAll())
                }) {
                    Text(text = "Clear")
                }
                Button(onClick = {
                    onEvent(Action.Submit)
                    focusManager.clearFocus()
                }) {
                    Text(text = "Register")
                }
            }
            if (state.success) {
                DialogItem(
                    title = "Registered Successfully", bodyText = """
                                Name     : ${state.name}
                                E Number : ${state.enrollNum}
                                Course   : ${state.course}
                                Barcode  : ${state.barcode}
                                Email    : ${state.email}
                            """.trimIndent()
                ) {
                    onEvent(Action.ClearAll())
                }

            }
            if (state.error.isNotEmpty()) {
                DialogItem(title = "Error", bodyText = state.error, isError = true) {
                    onEvent(Action.ClearAll(state.copy(error = "")))
                }
            }

        }
    }
}


@Preview
@Composable
fun Preview() {
    RegisterSection(
        RegisterStudentScreenState(
            success = true
        ),
        {}, {})
}

