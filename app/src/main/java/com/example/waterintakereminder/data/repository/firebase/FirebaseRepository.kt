package com.example.waterintakereminder.data.repository.firebase

import com.example.waterintakereminder.data.model.WaterIntake
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    suspend fun saveWaterIntake(intake: WaterIntake, userId: String): Result<Unit>
    suspend fun getWaterIntakes(userId: String): Result<List<WaterIntake>>
    fun observeWaterIntakes(userId: String): Flow<List<WaterIntake>>
    suspend fun updateWaterIntake(intake: WaterIntake, userId: String): Result<Unit>
    suspend fun deleteWaterIntake(intakeId: String, userId: String): Result<Unit>
}