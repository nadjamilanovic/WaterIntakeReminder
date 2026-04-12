package com.example.waterintakereminder.data.model

data class WaterIntake(
    val id: Int,
    val amountMl: Int,
    val time: String,
    val isDrank: Boolean = true
)