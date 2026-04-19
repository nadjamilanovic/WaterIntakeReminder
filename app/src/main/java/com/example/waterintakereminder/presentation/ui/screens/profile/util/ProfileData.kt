package com.example.waterintakereminder.presentation.ui.screens.profile.util

import com.example.waterintakereminder.data.model.WaterIntake

fun getProfileIntakes(): List<WaterIntake> {
    return listOf(
        WaterIntake(1, 250, "08:00", true),
        WaterIntake(2, 250, "10:30", true),
        WaterIntake(3, 200, "12:45", false),
        WaterIntake(4, 300, "15:20", true)
    )
}

fun getProfileAchievements(): List<AchievementModel> {
    return listOf(
        AchievementModel(1, "7 Day Streak", "7 days of hydration", "🔥"),
        AchievementModel(2, "100 Glasses", "100 glasses of water", "🥤"),
        AchievementModel(3, "Early Bird", "Morning hydration", "🌅"),
        AchievementModel(4, "Hydration Hero", "5000ml in one day", "💪"),
        AchievementModel(5, "2L Master", "Reached daily goal 10x", "🏆")
    )
}