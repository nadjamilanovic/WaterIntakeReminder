package com.example.waterintakereminder.data.model

data class ProfileData(
    val userName: String,
    val email: String,
    val joinDate: String,
    val dailyGoal: Int,
    val totalWaterConsumed: Int,
    val totalIntakes: Int,
    val averagePerDay: Int
)