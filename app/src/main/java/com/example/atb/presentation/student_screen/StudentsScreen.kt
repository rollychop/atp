package com.example.atb.presentation.student_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atb.domain.model.Student
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun StudentsScreen(
    state: StudentScreenState,
    navigator: DestinationsNavigator? = null
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
                    text = "Registered Student",
                    style = MaterialTheme.typography.bodyLarge
                )
            }


        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(modifier = Modifier) {
                items(state.students) { student ->
                    StudentItem(student = student)
                    Divider()
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
fun StudentItem(student: Student) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = .2f))
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = student.name,
            style = MaterialTheme.typography.headlineSmall
        )
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
    StudentsScreen(
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
        )
    )
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
