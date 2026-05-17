package com.example.waterintakereminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waterintakereminder.data.local.entity.AchievementEntity
import com.example.waterintakereminder.data.local.entity.UserAchievementEntity

@Dao
interface AchievementDao {

    @Insert
    suspend fun insertAchievements(achievements: List<AchievementEntity>)

    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievements(): List<AchievementEntity>

    @Insert
    suspend fun insertUserAchievement(userAchievement: UserAchievementEntity)

    @Insert
    suspend fun insertUserAchievements(userAchievements: List<UserAchievementEntity>)

    @Query("SELECT * FROM user_achievements WHERE user_id = :userId")
    suspend fun getUserAchievements(userId: Int): List<UserAchievementEntity>

    @Query("SELECT * FROM user_achievements WHERE user_id = :userId AND achievement_id = :achievementId")
    suspend fun isAchievementUnlocked(userId: Int, achievementId: Int): UserAchievementEntity?

    @Query("""
        SELECT a.* FROM achievements a
        INNER JOIN user_achievements ua ON a.id = ua.achievement_id
        WHERE ua.user_id = :userId
    """)
    suspend fun getUnlockedAchievementsForUser(userId: Int): List<AchievementEntity>
}