package com.example.atb.data.repository

import com.example.atb.data.data_source.ATBDao
import com.example.atb.domain.model.Stats
import com.example.atb.domain.repository.StatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StatsRepositoryImpl(
    private val atbDao: ATBDao
) : StatsRepository {
    override fun getHomeStats(): Flow<Stats> {
        return flow { emit(Stats(10, 20)) }
    }
}