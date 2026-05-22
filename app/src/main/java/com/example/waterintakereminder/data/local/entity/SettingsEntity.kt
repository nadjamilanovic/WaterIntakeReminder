package com.example.waterintakereminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    @ColumnInfo(name = "notifications_enabled")
    val notificationsEnabled: Boolean = false,
    @ColumnInfo(name = "reminder_enabled")
    val reminderEnabled: Boolean = false,
    @ColumnInfo(name = "reminder_time")
    val reminderTime: String = "09:00"
)