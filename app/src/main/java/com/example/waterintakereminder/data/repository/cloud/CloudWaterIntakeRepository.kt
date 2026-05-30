package com.example.waterintakereminder.data.repository.cloud

import com.example.waterintakereminder.data.datasource.network.dto.CreateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.UpdateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.WaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.service.WaterIntakeApiService
import javax.inject.Inject

class CloudWaterIntakeRepository @Inject constructor(
    private val apiService: WaterIntakeApiService
) : WaterIntakeCloudRepository {

    override suspend fun getAllWaterIntakes(): List<WaterIntakeDto> {
        return apiService.getAllWaterIntakes()
    }

    override suspend fun getWaterIntakeById(id: Int): WaterIntakeDto {
        return apiService.getWaterIntakeById(id)
    }

    override suspend fun createWaterIntake(createDto: CreateWaterIntakeDto): WaterIntakeDto {
        return apiService.createWaterIntake(createDto)
    }

    override suspend fun updateWaterIntake(id: Int, updateDto: UpdateWaterIntakeDto): WaterIntakeDto {
        return apiService.updateWaterIntake(id, updateDto)
    }

    override suspend fun deleteWaterIntake(id: Int) {
        apiService.deleteWaterIntake(id)
    }
}