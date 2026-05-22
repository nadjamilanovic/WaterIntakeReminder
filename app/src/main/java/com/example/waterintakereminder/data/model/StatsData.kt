package com.example.waterintakereminder.data.model

data class StatsData(
    val totalWater: Int,
    val totalDrinks: Int,
    val averageAmount: Int,
    val remainingWater: Int,
    val dailyGoal: Int = 2000
)