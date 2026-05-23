package com.example.waterintakereminder.data.datasource.network.dto

data class FirestoreWaterIntakeDto(
    val id: String = "",
    val amountMl: Int,
    val time: String,
    val isDrank: Boolean,
    val userId: String,
    val timestamp: Long = System.currentTimeMillis()
)

fun FirestoreWaterIntakeDto.toWaterIntake(): com.example.waterintakereminder.data.model.WaterIntake {
    return com.example.waterintakereminder.data.model.WaterIntake(
        id = 0,
        amountMl = this.amountMl,
        time = this.time,
        isDrank = this.isDrank
    )
}