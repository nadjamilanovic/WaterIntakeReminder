package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.dao.ProfileDao
import com.example.waterintakereminder.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override suspend fun insertProfile(profile: ProfileEntity) {
        profileDao.insertProfile(profile)
    }

    override suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }

    override fun observeProfile(): Flow<ProfileEntity?> {
        return profileDao.observeProfile()
    }

    override suspend fun updateProfile(profile: ProfileEntity) {
        profileDao.updateProfile(profile)
    }

    override suspend fun deleteProfile() {
        profileDao.deleteProfile()
    }
}