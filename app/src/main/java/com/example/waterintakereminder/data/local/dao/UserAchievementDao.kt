package com.example.waterintakereminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waterintakereminder.data.local.entity.UserAchievementEntity

@Dao
@Suppress("unused")
interface UserAchievementDao {

    @Insert
    suspend fun insertUserAchievements(userAchievements: List<UserAchievementEntity>)

    @Query("SELECT * FROM user_achievements WHERE user_id = :userId")
    suspend fun getUserAchievements(userId: Int): List<UserAchievementEntity>
}