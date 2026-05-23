package com.example.waterintakereminder.data.repository.firebase

import com.example.waterintakereminder.data.model.WaterIntake
import kotlinx.coroutines.flow.Flow

interface WaterIntakeFirestoreRepository {
    fun observeWaterIntakes(userId: String): Flow<List<WaterIntake>>
    suspend fun getWaterIntakes(userId: String): Result<List<WaterIntake>>
    suspend fun addWaterIntake(amountMl: Int, time: String, userId: String): Result<Unit>
    suspend fun toggleWaterIntake(intakeId: String, isDrank: Boolean, userId: String): Result<Unit>
    suspend fun deleteWaterIntake(intakeId: String, userId: String): Result<Unit>
}