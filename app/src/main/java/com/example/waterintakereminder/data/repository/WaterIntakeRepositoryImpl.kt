package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.dao.WaterIntakeDao
import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaterIntakeRepositoryImpl @Inject constructor(
    private val waterIntakeDao: WaterIntakeDao
) : WaterIntakeRepository {

    override suspend fun insertWaterIntake(waterIntake: WaterIntakeEntity) {
        waterIntakeDao.insertWaterIntake(waterIntake)
    }

    override suspend fun updateWaterIntake(waterIntake: WaterIntakeEntity) {
        waterIntakeDao.updateWaterIntake(waterIntake)
    }

    override suspend fun deleteWaterIntake(waterIntake: WaterIntakeEntity) {
        waterIntakeDao.deleteWaterIntake(waterIntake)
    }

    override fun getAllWaterIntakes(): Flow<List<WaterIntakeEntity>> {
        return waterIntakeDao.getAllWaterIntakes()
    }

    override suspend fun deleteAllWaterIntakes() {
        waterIntakeDao.deleteAllWaterIntakes()
    }
}