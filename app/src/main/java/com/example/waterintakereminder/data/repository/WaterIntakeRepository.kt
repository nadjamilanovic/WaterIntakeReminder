package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

interface WaterIntakeRepository {
    suspend fun insertWaterIntake(waterIntake: WaterIntakeEntity)
    suspend fun updateWaterIntake(waterIntake: WaterIntakeEntity)
    suspend fun deleteWaterIntake(waterIntake: WaterIntakeEntity)
    fun getAllWaterIntakes(): Flow<List<WaterIntakeEntity>>
    suspend fun deleteAllWaterIntakes()
}