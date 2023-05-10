package com.example.atb.di

import android.app.Application
import android.content.Context
import com.example.atb.data.repository.ScannerRepositoryImpl
import com.example.atb.domain.repository.ATBRepository
import com.example.atb.domain.repository.ScannerRepository
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object ScannerModule {
    @ViewModelScoped
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @ViewModelScoped
    @Provides
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @ViewModelScoped
    @Provides
    fun provideScannerRepo(
        context: Context,
        options: GmsBarcodeScannerOptions,
        atbRepository: ATBRepository
    ): ScannerRepository {
        return ScannerRepositoryImpl(GmsBarcodeScanning.getClient(context, options),atbRepository)
    }


}