package com.example.atb.presentation.register_student_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.domain.model.Student
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun RegisterStudentScreen(
    vm: RegisterStudentViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navigator?.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
                Text(
                    text = "Register Student",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var name by remember {
                mutableStateOf("")
            }
            var enrollNum by remember {
                mutableStateOf("")
            }
            var barcode by remember {
                mutableStateOf("")
            }
            var course by remember {
                mutableStateOf("")
            }
            var sem by remember {
                mutableStateOf("")
            }
            var email by remember {
                mutableStateOf("")
            }

            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                value = name,
                onValueChange = { inputName ->
                    name = inputName
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
                )

            )


            OutlinedTextField(
                value = enrollNum,
                onValueChange = { inputEnrollNum ->
                    enrollNum = inputEnrollNum
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
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )



            OutlinedTextField(
                value = barcode,
                onValueChange = { inputBarcode ->
                    barcode = inputBarcode
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
                    Icon(
                        imageVector = Icons.Rounded.QrCodeScanner,
                        contentDescription = "Qr Scanner"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )


            OutlinedTextField(
                value = course,
                onValueChange = { inputCourse ->
                    course = inputCourse
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
                )
            )


            OutlinedTextField(
                value = sem,
                onValueChange = { inputSem ->
                    sem = inputSem
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
                )
            )


            OutlinedTextField(
                value = email,
                onValueChange = { inputEmail ->
                    email = inputEmail
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
                    imeAction = ImeAction.Done
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    name = ""
                    enrollNum = ""
                    barcode = ""
                    course = ""
                    sem = ""
                    email = ""
                }) {
                    Text(text = "Clear")
                }
                val ctx = LocalContext.current
                Button(onClick = {
                    val isValid = validate(
                        name, enrollNum, barcode, course, sem, email
                    )
                    if (isValid.first.not()) {
                        Toast.makeText(ctx, isValid.second, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    vm.register(
                        Student(
                            name, enrollNum, course, sem.toInt(), email, barcode.toInt()
                        )
                    )
                    Toast.makeText(ctx, "Student Registered", Toast.LENGTH_SHORT).show()
                    name = ""
                    enrollNum = ""
                    barcode = ""
                    course = ""
                    sem = ""
                    email = ""
                    focusManager.clearFocus()
                }) {
                    Text(text = "Register")
                }
            }

        }
    }

}

fun validate(
    name: String,
    enrollNum: String,
    barcode: String,
    course: String,
    sem: String,
    email: String
): Pair<Boolean, String> {
    return if (name.isEmpty()) false to "Enter Valid Name"
    else if (name.length < 4) false to "Name must be at least 4 characters"
    else if (enrollNum.isEmpty()) false to "Enrollment number is Empty"
    else if (barcode.isEmpty()) false to "Barcode is Empty"
    else if (barcode.any { it.isDigit().not() }) false to "Barcode must be a number"
    else if (course.isEmpty()) false to "Course is Empty"
    else if (sem.isEmpty()) false to "Semester is Empty"
    else if (sem.all { it.isDigit().not() } || sem.toInt() !in 1..8) false to "Invalid Semester"
    else if (email.matches(
            Regex(
                "[a-zA-Z0-9+._%\\-]{1,256}" +
                        "@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )
        ).not()
    ) false to "Invalid Email"
    else return (true to "")


}


@Preview
@Composable
fun Preview() {
    RegisterStudentScreen()
}
