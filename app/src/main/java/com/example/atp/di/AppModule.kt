package com.example.atp.di

import android.app.Application
import androidx.room.Room
import com.example.atp.data.data_source.ATPDatabase
import com.example.atp.data.repository.ATPRepositoryImpl
import com.example.atp.domain.repository.ATPRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ATPDatabase {
        return Room.databaseBuilder(
            app,
            ATPDatabase::class.java,
            ATPDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: ATPDatabase): ATPRepository {
        return ATPRepositoryImpl(db.atpDao)
    }

}