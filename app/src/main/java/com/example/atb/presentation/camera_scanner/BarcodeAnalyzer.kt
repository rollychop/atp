package com.example.atb.presentation.camera_scanner

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.atb.presentation.camera_scanner.BarcodeAnalyzerResult.*
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.lang.Exception

class BarcodeAnalyser(
    val callback: (BarcodeAnalyzerResult) -> Unit
) : ImageAnalysis.Analyzer {
    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.size > 0) {
                        callback(Success(barcodes))
                    } else callback(NotFound)
                }
                .addOnFailureListener { exception ->
                    callback(Failure(exception))
                }
                .addOnCompleteListener {
                    imageProxy.close()
                    mediaImage.close()
                }
        }
    }
}


sealed interface BarcodeAnalyzerResult {
    data class Success(val barcodes: List<Barcode>) : BarcodeAnalyzerResult
    object NotFound : BarcodeAnalyzerResult
    data class Failure(val exception: Exception) : BarcodeAnalyzerResult
}