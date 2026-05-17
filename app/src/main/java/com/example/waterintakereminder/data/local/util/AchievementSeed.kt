package com.example.waterintakereminder.data.local.util

import com.example.waterintakereminder.data.local.entity.AchievementEntity

fun getSampleAchievements(): List<AchievementEntity> {
    return listOf(
        AchievementEntity(1, "First Drop", "First glass of water logged!", "💧", 250),
        AchievementEntity(2, "Getting Started", "Logged 1 liter total", "🥤", 1000),
        AchievementEntity(3, "Hydration Hero", "Logged 5 liters total", "💪", 5000),
        AchievementEntity(4, "Morning Warrior", "Logged water before 9 AM", "🌅", 250),
        AchievementEntity(5, "Perfect Day", "Reached daily goal", "🏆", 2000),
        AchievementEntity(6, "Consistency", "7 day streak", "🔥", 3500),
        AchievementEntity(7, "Water Master", "Logged 10 liters total", "👑", 10000)
    )
}