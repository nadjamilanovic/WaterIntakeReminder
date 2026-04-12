package com.example.waterintakereminder.data.model

object HardcodedData {
    val sampleIntakes = listOf(
        WaterIntake(1, 250, "08:00", true),
        WaterIntake(2, 250, "10:30", true),
        WaterIntake(3, 200, "12:45", false),
        WaterIntake(4, 300, "15:20", true)
    )
}