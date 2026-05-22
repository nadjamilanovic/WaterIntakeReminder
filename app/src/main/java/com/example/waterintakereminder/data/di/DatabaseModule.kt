package com.example.waterintakereminder.data.di

import android.content.Context
import androidx.room.Room
import com.example.waterintakereminder.data.local.dao.WaterIntakeDao
import com.example.waterintakereminder.data.local.dao.AchievementDao
import com.example.waterintakereminder.data.local.dao.UserAchievementDao
import com.example.waterintakereminder.data.local.dao.ProfileDao
import com.example.waterintakereminder.data.local.dao.SettingsDao
import com.example.waterintakereminder.data.local.dao.TipDao
import com.example.waterintakereminder.data.local.db.WaterDatabase
import com.example.waterintakereminder.data.repository.AchievementRepository
import com.example.waterintakereminder.data.repository.AchievementRepositoryImpl
import com.example.waterintakereminder.data.repository.ProfileRepository
import com.example.waterintakereminder.data.repository.ProfileRepositoryImpl
import com.example.waterintakereminder.data.repository.WaterIntakeRepository
import com.example.waterintakereminder.data.repository.WaterIntakeRepositoryImpl
import com.example.waterintakereminder.data.repository.SettingsRepository
import com.example.waterintakereminder.data.repository.SettingsRepositoryImpl
import com.example.waterintakereminder.data.repository.TipRepository
import com.example.waterintakereminder.data.repository.TipRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WaterDatabase {
        return Room.databaseBuilder(
            context,
            WaterDatabase::class.java,
            "water_database"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideWaterIntakeDao(database: WaterDatabase): WaterIntakeDao {
        return database.waterIntakeDao()
    }

    @Provides
    @Singleton
    fun provideAchievementDao(database: WaterDatabase): AchievementDao {
        return database.achievementDao()
    }

    @Provides
    @Singleton
    fun provideUserAchievementDao(database: WaterDatabase): UserAchievementDao {
        return database.userAchievementDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(database: WaterDatabase): ProfileDao {
        return database.profileDao()
    }

    @Provides
    @Singleton
    fun provideSettingsDao(database: WaterDatabase): SettingsDao {
        return database.settingsDao()
    }

    @Provides
    @Singleton
    fun provideTipDao(database: WaterDatabase): TipDao {
        return database.tipDao()
    }

    @Provides
    @Singleton
    fun provideAchievementRepository(achievementDao: AchievementDao): AchievementRepository {
        return AchievementRepositoryImpl(achievementDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileDao: ProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(profileDao)
    }

    @Provides
    @Singleton
    fun provideWaterIntakeRepository(waterIntakeDao: WaterIntakeDao): WaterIntakeRepository {
        return WaterIntakeRepositoryImpl(waterIntakeDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsDao: SettingsDao): SettingsRepository {
        return SettingsRepositoryImpl(settingsDao)
    }

    @Provides
    @Singleton
    fun provideTipRepository(tipDao: TipDao): TipRepository {
        return TipRepositoryImpl(tipDao)
    }
}