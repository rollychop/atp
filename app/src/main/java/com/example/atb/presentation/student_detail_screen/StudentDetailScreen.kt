package com.example.atb.presentation.student_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import com.example.atb.domain.model.StudentDetail
import com.example.atb.presentation.util.WithTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Composable
@Destination(navArgsDelegate = Student::class)
fun StudentDetailScreen(
    viewModel: StudentDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    StudentDetailSection(
        state = viewModel.state.value,
        onBackPressed = navigator::popBackStack
    )

}

@Composable
fun StudentDetailSection(
    state: StudentDetailScreenState,
    onBackPressed: () -> Unit
) {
    WithTopAppBar(
        title = "Student Detail",
        onLeftIconClick = onBackPressed
    ) {
        if (state.studentDetail == null) {
            Text(
                text = "Unable to Load Student",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            return@WithTopAppBar
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = .2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getTwoChars(state.studentDetail.student.name),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp

                        )
                    }
                    Text(text = state.studentDetail.student.name)
                }
            }
            item {

            }
            if (state.studentDetail.attendanceLogs.isNotEmpty()) {
                item {
                    Text(text = "All Attendance Logs")
                }
            }
            items(state.studentDetail.attendanceLogs) { log ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${log.date}")
                    Text(text = log.subject)
                }
            }
        }
    }
}

private fun getTwoChars(name: String): String {
    val split = name.trim().split(" ")
    return if (split.size == 1) name.subSequence(0, 2).toString().uppercase()
    else "${split.first().take(1)}${split.last().take(1)}".uppercase()
}

@Preview
@Composable
fun StudentDetailPreview() {
    StudentDetailSection(
        state = StudentDetailScreenState(
            studentDetail = StudentDetail(
                Student(
                    "Rohit K",
                    "MC/21/016",
                    "MCA",
                    4,
                    "rollychop@gmail.com",
                    6655
                ),
                listOf(
                    AttendanceLog(
                        barcode = 6655,
                        date = LocalDateTime.now().toKotlinLocalDateTime(),
                        subject = "CG"
                    ),
                    AttendanceLog(
                        barcode = 6655,
                        date = LocalDateTime.now().minusDays(1).toKotlinLocalDateTime(),
                        subject = "CG"
                    ),
                    AttendanceLog(
                        barcode = 6655,
                        date = LocalDateTime.now().minusDays(2).toKotlinLocalDateTime(),
                        subject = "CG"
                    ),
                    AttendanceLog(
                        barcode = 6655,
                        date = LocalDateTime.now().minusDays(3).toKotlinLocalDateTime(),
                        subject = "CG"
                    ),
                )
            )
        )
    ) {}
}

