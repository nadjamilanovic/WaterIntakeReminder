package com.example.waterintakereminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "water_intakes")
data class WaterIntakeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "amount_ml")
    val amountMl: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: String,
    @ColumnInfo(name = "is_drank")
    val isDrank: Boolean = true,
    @ColumnInfo(name = "user_id")
    val userId: Int = 1
)