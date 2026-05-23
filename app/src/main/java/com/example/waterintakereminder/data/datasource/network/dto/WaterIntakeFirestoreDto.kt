package com.example.waterintakereminder.data.datasource.network.dto

import com.example.waterintakereminder.data.model.WaterIntake

data class WaterIntakeFirestoreDto(
    val amountMl: Int,
    val time: String,
    val isDrank: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

fun WaterIntakeFirestoreDto.toWaterIntake(): WaterIntake {
    return WaterIntake(
        id = 0,
        amountMl = this.amountMl,
        time = this.time,
        isDrank = this.isDrank
    )
}