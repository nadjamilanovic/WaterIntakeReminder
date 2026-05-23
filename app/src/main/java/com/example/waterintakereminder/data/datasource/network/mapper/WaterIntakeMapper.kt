package com.example.waterintakereminder.data.datasource.network.mapper

import com.example.waterintakereminder.data.datasource.network.dto.WaterIntakeDto
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.data.datasource.network.dto.CreateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.UpdateWaterIntakeDto

fun WaterIntakeDto.toWaterIntake(): WaterIntake {
    return WaterIntake(
        id = this.id,
        amountMl = this.amountMl,
        time = this.timestamp,
        isDrank = this.isDrank
    )
}

fun WaterIntake.toCreateWaterIntakeDto(userId: Int = 1): CreateWaterIntakeDto {
    return CreateWaterIntakeDto(
        amountMl = this.amountMl,
        timestamp = this.time,
        isDrank = this.isDrank,
        userId = userId
    )
}

fun WaterIntake.toUpdateWaterIntakeDto(): UpdateWaterIntakeDto {
    return UpdateWaterIntakeDto(
        amountMl = this.amountMl,
        timestamp = this.time,
        isDrank = this.isDrank,
        userId = 1
    )
}