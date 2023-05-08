package com.example.atb.presentation.attendance_log_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.example.atb.domain.model.AttendanceLog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Composable
@Destination
fun AttendanceLogScreen(
    state: AttendanceLogScreenState,
    navigator: DestinationsNavigator
) {
    AttendanceLogSection(
        isStudent = state.student != null,
        onBackPressed = { navigator.popBackStack() },
        attendanceLogs = state.attendanceLogs
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceLogSection(
    isStudent: Boolean,
    onBackPressed: () -> Unit,
    attendanceLogs: List<AttendanceLog>
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
                Text(
                    text = "All Attendance",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (!isStudent) {
                LazyColumn {
                    items(attendanceLogs) { attendanceLog ->
                        AttendanceLogItem(attendanceLog = attendanceLog)
                    }
                }
            }
        }
    }
}

@Composable
fun AttendanceLogItem(
    attendanceLog: AttendanceLog
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = attendanceLog.barcode.toString())
        Text(
            text = attendanceLog.date.toString()
                .split('.')[0]
                .replace("T", " -- ")
        )
        Text(text = attendanceLog.subject.take(5), maxLines = 1)
    }
}

@Preview
@Composable
fun MarkAttendancePreview() {
    AttendanceLogSection(
        isStudent = false,
        onBackPressed = { },
        attendanceLogs = listOf(
            AttendanceLog(
                5544, null, LocalDateTime.now().toKotlinLocalDateTime(), "CG"
            ),
            AttendanceLog(
                4543, null, LocalDateTime.now().toKotlinLocalDateTime(), "CG"
            ),
            AttendanceLog(
                1145, null, LocalDateTime.now().toKotlinLocalDateTime(), "CG"
            ),
        )
    )
}