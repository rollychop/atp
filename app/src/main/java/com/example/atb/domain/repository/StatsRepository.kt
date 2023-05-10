package com.example.atb.domain.repository

import com.example.atb.domain.model.Stats
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getHomeStats(): Flow<Stats>
}