package com.example.waterintakereminder.data.local.util

import com.example.waterintakereminder.data.local.entity.UserAchievementEntity

fun getSampleUserAchievements(userId: Int): List<UserAchievementEntity> {
    return listOf(
        UserAchievementEntity(userId = userId, achievementId = 1, unlockedAt = System.currentTimeMillis()),
        UserAchievementEntity(userId = userId, achievementId = 2, unlockedAt = System.currentTimeMillis())
    )
}