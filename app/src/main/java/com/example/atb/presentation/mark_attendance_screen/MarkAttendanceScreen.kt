package com.example.atb.presentation.mark_attendance_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.presentation.util.WithTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Destination
@Composable
fun MarkAttendanceScreen(
    viewModel: MarkAttendanceViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    MarkAttendanceSection(
        onSubmit = viewModel::mark,
        onBackPress = { navigator.popBackStack() },
        state = viewModel.state.collectAsState().value,
        onScanClicked = { viewModel.startScanningAndMarkAttendance() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkAttendanceSection(
    onSubmit: (AttendanceLog) -> Unit,
    onBackPress: () -> Unit,
    state: MarkAttendanceScreenState,
    onScanClicked: (() -> Unit)? = null
) {
    WithTopAppBar(
        onLeftIconClick = onBackPress,
        title = "Mark Attendance"
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            var barcode by remember {
                mutableStateOf("")
            }
            val ctx = LocalContext.current
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
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (barcode.all { it.isDigit() }.not()) {
                            Toast.makeText(ctx, "Invalid barcode", Toast.LENGTH_SHORT).show()
                            return@KeyboardActions
                        }

                        onSubmit(
                            AttendanceLog(
                                barcode = barcode.toInt(),
                                date = LocalDateTime.now().toKotlinLocalDateTime(),
                            )
                        )
                        Toast.makeText(ctx, "Marked Successfully", Toast.LENGTH_SHORT).show()
                    }
                )
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {

                    if (barcode.all { it.isDigit() }.not()) {
                        Toast.makeText(ctx, "Invalid barcode", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    onSubmit(
                        AttendanceLog(
                            barcode = barcode.toInt(),
                            date = LocalDateTime.now().toKotlinLocalDateTime(),
                        )
                    )
                    Toast.makeText(ctx, "Marked Successfully", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Mark Attendance")
                }

            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        onScanClicked?.invoke()
                    },
                    modifier = Modifier.size(128.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.QrCodeScanner,
                        contentDescription = "Scan Qr Code",
                        modifier = Modifier.size(512.dp)
                    )
                }
                Text(text = "Scan and Mark", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(32.dp))
                if (state.student != null) {
                    Text(
                        text = "Marked Attendance for ${state.student.name} Successfully",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                if (state.error.isNotEmpty()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }


        }
    }
}

@Preview
@Composable
fun MarkPreview() {
    MarkAttendanceSection(
        {}, {},
        MarkAttendanceScreenState("Hello Some error")
    )
}

