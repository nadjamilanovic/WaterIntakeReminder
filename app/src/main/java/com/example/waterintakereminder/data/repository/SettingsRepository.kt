package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.entity.SettingsEntity

interface SettingsRepository {
    suspend fun getSettings(): SettingsEntity?
    suspend fun updateSettings(settings: SettingsEntity)
    suspend fun insertDefaultSettings()
}