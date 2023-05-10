package com.example.atb.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.atb.domain.model.AttendanceLog
import com.example.atb.domain.model.Student
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.domain.repository.ScannerRepository
import com.example.atb.util.Resource
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.io.IOException
import java.time.LocalDateTime

private const val TAG = "ScannerRepositoryImpl"

class ScannerRepositoryImpl(
    private val scanner: GmsBarcodeScanner,
    private val atbRepository: ATBRepository
) :
    ScannerRepository {

    override fun startScanningAndMarkAttendance(): Flow<Resource<Student?>> {
        return callbackFlow {
            launch {
                send(Resource.Loading())
            }
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        try {
                            if (barcode.format != Barcode.FORMAT_CODE_128) {
                                send(Resource.Error("Invalid barcode Scanned"))
                                return@launch
                            }

                            if (barcode.rawValue.isNullOrEmpty() || barcode.rawValue!!.trim()
                                    .isDigitsOnly().not()
                            ) {
                                send(Resource.Error("Not Valid Barcode"))
                                return@launch
                            }
                            val barcodeValue = barcode.rawValue!!.toInt()
                            atbRepository.insertAttendance(
                                AttendanceLog(
                                    barcode = barcodeValue,
                                    date = LocalDateTime.now().toKotlinLocalDateTime()
                                )
                            )
                            val student = atbRepository.getStudentByBarcode(barcodeValue)
                            send(Resource.Success(student))
                        } catch (ex: SQLiteConstraintException) {
                            send(Resource.Error("Student is Not Registered"))
                        } catch (io: IOException) {
                            send(Resource.Error(io.message ?: "IO Error"))
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "failure: ", exception)
                    launch {
                        send(Resource.Error(exception.localizedMessage ?: " Scanning Failed"))
                    }

                }
            awaitClose { }
        }
    }

    override fun startScanning(

    ): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send("${it.rawValue}")
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }

    }
}