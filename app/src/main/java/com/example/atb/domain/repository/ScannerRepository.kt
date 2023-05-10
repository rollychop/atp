package com.example.atb.domain.repository

import com.example.atb.domain.model.Student
import com.example.atb.util.Resource
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {
    fun startScanning(): Flow<String?>

    fun startScanningAndMarkAttendance(): Flow<Resource<Student?>>
}