package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.dao.SettingsDao
import com.example.waterintakereminder.data.local.entity.SettingsEntity
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    override suspend fun getSettings(): SettingsEntity? {
        return settingsDao.getSettings()
    }

    override suspend fun updateSettings(settings: SettingsEntity) {
        settingsDao.updateSettings(settings)
    }

    override suspend fun insertDefaultSettings() {
        if (settingsDao.getSettings() == null) {
            settingsDao.insertSettings(SettingsEntity())
        }
    }
}