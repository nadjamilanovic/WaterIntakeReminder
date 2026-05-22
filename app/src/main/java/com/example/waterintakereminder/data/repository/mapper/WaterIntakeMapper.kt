package com.example.waterintakereminder.data.repository.mapper

import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import com.example.waterintakereminder.data.model.WaterIntake

fun WaterIntakeEntity.toWaterIntake(): WaterIntake {
    return WaterIntake(
        id = this.id,
        amountMl = this.amountMl,
        time = this.timestamp,
        isDrank = this.isDrank
    )
}

fun WaterIntake.toWaterIntakeEntity(): WaterIntakeEntity {
    return WaterIntakeEntity(
        id = this.id,
        amountMl = this.amountMl,
        timestamp = this.time,
        isDrank = this.isDrank,
        userId = 1
    )
}