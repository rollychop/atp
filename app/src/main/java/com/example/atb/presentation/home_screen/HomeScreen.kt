package com.example.atb.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atb.presentation.destinations.AttendanceLogScreenDestination
import com.example.atb.presentation.destinations.MarkAttendanceScreenDestination
import com.example.atb.presentation.destinations.RegisterStudentScreenDestination
import com.example.atb.presentation.destinations.StudentsScreenDestination
import com.example.atb.presentation.home_screen.HomeScreenAction.MARK_ATTENDANCE
import com.example.atb.presentation.home_screen.HomeScreenAction.REGISTER
import com.example.atb.presentation.home_screen.HomeScreenAction.VIEW_ATTENDANCE
import com.example.atb.presentation.home_screen.HomeScreenAction.VIEW_STUDENT
import com.example.atb.presentation.util.WithTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    HomeSection { action ->
        when (action) {
            VIEW_STUDENT -> navigator.navigate(StudentsScreenDestination)
            VIEW_ATTENDANCE -> navigator.navigate(AttendanceLogScreenDestination)
            REGISTER -> navigator.navigate(RegisterStudentScreenDestination)
            MARK_ATTENDANCE -> navigator.navigate(MarkAttendanceScreenDestination)
        }

    }
}

@Composable
fun HomeSection(
    onAction: (HomeScreenAction) -> Unit
) {
    WithTopAppBar(
        leftIcon = null,
        title = "Dashboard"
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    onAction(VIEW_STUDENT)
                }) {
                    Text(text = "View Students")
                }
                Button(onClick = {
                    onAction(REGISTER)
                }) {
                    Text(text = "Register Student")
                }

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    onAction(VIEW_ATTENDANCE)
                }) {
                    Text(text = "Attendance Log")
                }
                Button(onClick = {
                    onAction(MARK_ATTENDANCE)
                }) {
                    Text(text = "Take attendance")
                }

            }
        }
    }
}

enum class HomeScreenAction {
    VIEW_STUDENT,
    VIEW_ATTENDANCE,
    REGISTER,
    MARK_ATTENDANCE
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeSection {}
}

