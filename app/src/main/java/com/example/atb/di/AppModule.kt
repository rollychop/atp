package com.example.atb.di

import android.app.Application
import androidx.room.Room
import com.example.atb.data.data_source.ATBDatabase
import com.example.atb.data.repository.ATBRepositoryImpl
import com.example.atb.data.repository.StatsRepositoryImpl
import com.example.atb.domain.repository.ATBRepository
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
    fun provideATBDatabase(app: Application): ATBDatabase {
        return Room.databaseBuilder(
            app,
            ATBDatabase::class.java,
            ATBDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideATBRepository(db: ATBDatabase): ATBRepository {
        return ATBRepositoryImpl(db.atbDao)
    }

    @Provides
    @Singleton
    fun provideStatsRepository(db: ATBDatabase): StatsRepositoryImpl {
        return StatsRepositoryImpl(db.atbDao)
    }



}