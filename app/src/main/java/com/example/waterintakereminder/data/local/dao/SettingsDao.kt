package com.example.waterintakereminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.waterintakereminder.data.local.entity.SettingsEntity

@Dao
interface SettingsDao {

    @Insert
    suspend fun insertSettings(settings: SettingsEntity)

    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettings(): SettingsEntity?

    @Update
    suspend fun updateSettings(settings: SettingsEntity)
}