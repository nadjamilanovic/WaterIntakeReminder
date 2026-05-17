package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.dao.AchievementDao
import com.example.waterintakereminder.data.local.entity.UserAchievementEntity
import com.example.waterintakereminder.data.local.util.getSampleAchievements
import com.example.waterintakereminder.data.local.util.getSampleUserAchievements
import javax.inject.Inject
import com.example.waterintakereminder.data.local.entity.AchievementEntity

class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override suspend fun seedAchievements() {
        if (achievementDao.getAllAchievements().isEmpty()) {
            achievementDao.insertAchievements(getSampleAchievements())
        }
    }

    override suspend fun getAllAchievements(): List<AchievementEntity> {
        return achievementDao.getAllAchievements()
    }

    override suspend fun getUnlockedAchievementsForUser(userId: Int): List<AchievementEntity> {
        return achievementDao.getUnlockedAchievementsForUser(userId)
    }

    override suspend fun unlockAchievement(userId: Int, achievementId: Int) {
        val exists = achievementDao.isAchievementUnlocked(userId, achievementId)
        if (exists == null) {
            achievementDao.insertUserAchievement(
                UserAchievementEntity(userId = userId, achievementId = achievementId)
            )
        }
    }

    override suspend fun seedUserAchievements(userId: Int) {
        val existing = achievementDao.getUserAchievements(userId)
        if (existing.isEmpty()) {
            achievementDao.insertUserAchievements(getSampleUserAchievements(userId))
        }
    }
}