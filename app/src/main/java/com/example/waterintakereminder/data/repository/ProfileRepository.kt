package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun insertProfile(profile: ProfileEntity)
    suspend fun getProfile(): ProfileEntity?
    fun observeProfile(): Flow<ProfileEntity?>
    suspend fun updateProfile(profile: ProfileEntity)
    suspend fun deleteProfile()
}