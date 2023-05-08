package com.example.atb.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.presentation.attendance_log_screen.AttendanceLogViewModel
import com.example.atb.presentation.destinations.AttendanceLogScreenDestination
import com.example.atb.presentation.destinations.MarkAttendanceScreenDestination
import com.example.atb.presentation.destinations.RegisterStudentScreenDestination
import com.example.atb.presentation.destinations.StudentsScreenDestination
import com.example.atb.presentation.student_screen.StudentsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator? = null
) {
    val studentsViewModel: StudentsViewModel = hiltViewModel()
    val attendanceLogVM: AttendanceLogViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Home",
                    style = MaterialTheme.typography.headlineMedium
                )

            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier =
            Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    navigator?.navigate(StudentsScreenDestination(studentsViewModel.state.value))
                }) {
                    Text(text = "View Students")
                }
                Button(onClick = {
                    navigator?.navigate(RegisterStudentScreenDestination)
                }) {
                    Text(text = "Register Student")
                }

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    navigator?.navigate(AttendanceLogScreenDestination(attendanceLogVM.state.value))
                }) {
                    Text(text = "Attendance Log")
                }
                Button(onClick = {
                    navigator
                        ?.navigate(MarkAttendanceScreenDestination())
                }) {
                    Text(text = "Take attendance")
                }

            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}