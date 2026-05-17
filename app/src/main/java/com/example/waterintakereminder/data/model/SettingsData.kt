package com.example.waterintakereminder.data.model

data class SettingsData(
    val notificationsEnabled: Boolean,
    val reminderEnabled: Boolean,
    val reminderTime: String = "09:00"
)