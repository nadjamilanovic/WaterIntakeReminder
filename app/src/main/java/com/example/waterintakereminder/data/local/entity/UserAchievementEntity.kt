package com.example.waterintakereminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.ColumnInfo

@Entity(
    tableName = "user_achievements",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AchievementEntity::class,
            parentColumns = ["id"],
            childColumns = ["achievement_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["achievement_id"])
    ]
)
data class UserAchievementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "achievement_id")
    val achievementId: Int,
    @ColumnInfo(name = "unlocked_at")
    val unlockedAt: Long = System.currentTimeMillis()
)