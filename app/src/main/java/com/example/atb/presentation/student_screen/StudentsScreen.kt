package com.example.atb.presentation.student_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.domain.model.Student
import com.example.atb.presentation.destinations.StudentDetailScreenDestination
import com.example.atb.presentation.util.WithTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun StudentsScreen(
    viewModel: StudentsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    StudentsListSection(state = viewModel.state.value,
        onBackPressed = { navigator.popBackStack() },
        onStudentItemClick = { student ->
            navigator.navigate(StudentDetailScreenDestination(student))
        }
    )

}

@Composable
fun StudentsListSection(
    state: StudentScreenState,
    onBackPressed: () -> Unit,
    onStudentItemClick: (Student) -> Unit
) {
    WithTopAppBar(
        onLeftIconClick = onBackPressed,
        title = "All Students"
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(state.students) { student ->
                    StudentItem(
                        student = student,
                        onClick = onStudentItemClick
                    )
                }

            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            if (state.error.isNotEmpty()) {
                Text(
                    text = state.error,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }


        }

    }
}

@Composable
fun StudentItem(
    modifier: Modifier = Modifier,
    student: Student,
    onClick: (Student) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(student) }
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .2f))
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(text = student.barcode.toString())
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = student.course)
            Text(text = student.enrollNumber)
        }
    }
}

@Preview
@Composable
fun StudentsPreview() {
    StudentsListSection(
        StudentScreenState(
            students = listOf(
                Student(
                    "Rohit Kumar",
                    "MC/21/016",
                    "MCA",
                    4,
                    "rohit@gmail.com",
                    5566
                ),
                Student(
                    "Vickey Kumar",
                    "MC/21/026",
                    "MCA",
                    4,
                    "vickey@gmail.com",
                    5365
                )
            )
        ), {}, {})
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StudentItemPreview() {
    Scaffold { p ->
        Box(modifier = Modifier.padding(p)) {
            StudentItem(
                student = Student(
                    "Rohit Kumar",
                    "MC/21/016",
                    "MCA",
                    4,
                    "rollychop@gmail.com",
                    5566
                )
            )
        }

    }
}*/
