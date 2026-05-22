package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.entity.AchievementEntity

interface AchievementRepository {
    suspend fun seedAchievements()
    suspend fun getAllAchievements(): List<AchievementEntity>
    suspend fun getUnlockedAchievementsForUser(userId: Int): List<AchievementEntity>
    suspend fun unlockAchievement(userId: Int, achievementId: Int)
    suspend fun seedUserAchievements(userId: Int)
}