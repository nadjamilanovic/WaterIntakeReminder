package com.example.waterintakereminder.data.repository.cloud

import com.example.waterintakereminder.data.datasource.network.dto.CreateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.UpdateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.WaterIntakeDto

interface WaterIntakeCloudRepository {
    suspend fun getAllWaterIntakes(): List<WaterIntakeDto>
    suspend fun getWaterIntakeById(id: Int): WaterIntakeDto
    suspend fun createWaterIntake(createDto: CreateWaterIntakeDto): WaterIntakeDto
    suspend fun updateWaterIntake(id: Int, updateDto: UpdateWaterIntakeDto): WaterIntakeDto
    suspend fun deleteWaterIntake(id: Int)
}