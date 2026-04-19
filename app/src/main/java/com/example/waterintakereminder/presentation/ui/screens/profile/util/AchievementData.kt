package com.example.waterintakereminder.presentation.ui.screens.profile.util

data class AchievementModel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val icon: String
)

fun getSampleAchievements(): List<AchievementModel> {
    return listOf(
        AchievementModel(1, "7 Day Streak", "7 days of hydration", "🔥"),
        AchievementModel(2, "100 Glasses", "100 glasses of water", "🥤"),
        AchievementModel(3, "Early Bird", "Morning hydration", "🌅"),
        AchievementModel(4, "Hydration Hero", "5000ml in one day", "💪"),
        AchievementModel(5, "2L Master", "Reached daily goal 10x", "🏆"),
        AchievementModel(6, "Perfect Week", "7/7 days met goal", "⭐"),
        AchievementModel(7, "Night Owl", "Late night hydration", "🦉"),
        AchievementModel(8, "Consistency King", "30 day streak", "👑")
    )
}