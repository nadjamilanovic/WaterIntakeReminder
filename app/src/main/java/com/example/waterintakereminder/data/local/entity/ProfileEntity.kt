package com.example.waterintakereminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "daily_goal")
    val dailyGoal: Int = 2000,
    @ColumnInfo(name = "join_date")
    val joinDate: String,
    @ColumnInfo(name = "total_water_consumed")
    val totalWaterConsumed: Int = 0,
    @ColumnInfo(name = "total_intakes")
    val totalIntakes: Int = 0,
    @ColumnInfo(name = "average_per_day")
    val averagePerDay: Int = 0
)