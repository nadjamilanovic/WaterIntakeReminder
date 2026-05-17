package com.example.waterintakereminder.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.waterintakereminder.data.local.dao.WaterIntakeDao
import com.example.waterintakereminder.data.local.dao.SettingsDao
import com.example.waterintakereminder.data.local.dao.AchievementDao
import com.example.waterintakereminder.data.local.dao.UserAchievementDao
import com.example.waterintakereminder.data.local.dao.ProfileDao
import com.example.waterintakereminder.data.local.dao.TipDao
import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import com.example.waterintakereminder.data.local.entity.AchievementEntity
import com.example.waterintakereminder.data.local.entity.UserAchievementEntity
import com.example.waterintakereminder.data.local.entity.ProfileEntity
import com.example.waterintakereminder.data.local.entity.SettingsEntity
import com.example.waterintakereminder.data.local.entity.TipEntity
import com.example.waterintakereminder.data.local.util.Converters

@Database(
    entities = [
        ProfileEntity::class,
        AchievementEntity::class,
        UserAchievementEntity::class,
        WaterIntakeEntity::class,
        SettingsEntity::class,
        TipEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WaterDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun achievementDao(): AchievementDao
    abstract fun waterIntakeDao(): WaterIntakeDao
    abstract fun userAchievementDao(): UserAchievementDao
    abstract fun settingsDao(): SettingsDao
    abstract fun tipDao(): TipDao

    companion object {
        @Volatile
        private var INSTANCE: WaterDatabase? = null

        fun getDatabase(context: Context): WaterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterDatabase::class.java,
                    "water_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}