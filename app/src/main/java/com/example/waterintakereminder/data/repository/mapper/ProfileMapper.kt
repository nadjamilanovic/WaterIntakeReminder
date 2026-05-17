package com.example.waterintakereminder.data.repository.mapper

import com.example.waterintakereminder.data.local.entity.ProfileEntity
import com.example.waterintakereminder.data.model.ProfileData

fun ProfileEntity.toProfileData(): ProfileData {
    return ProfileData(
        userName = this.userName,
        email = "waterwarrior@example.com",
        joinDate = this.joinDate,
        dailyGoal = this.dailyGoal,
        totalWaterConsumed = this.totalWaterConsumed,
        totalIntakes = this.totalIntakes,
        averagePerDay = this.averagePerDay
    )
}

fun ProfileData.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        id = 1,
        userName = this.userName,
        dailyGoal = this.dailyGoal,
        joinDate = this.joinDate,
        totalWaterConsumed = this.totalWaterConsumed,
        totalIntakes = this.totalIntakes,
        averagePerDay = this.averagePerDay
    )
}