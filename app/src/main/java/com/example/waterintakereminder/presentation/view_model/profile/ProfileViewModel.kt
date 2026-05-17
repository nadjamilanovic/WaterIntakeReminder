package com.example.waterintakereminder.presentation.view_model.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.repository.ProfileRepository
import com.example.waterintakereminder.data.repository.WaterIntakeRepository
import com.example.waterintakereminder.data.repository.AchievementRepository
import com.example.waterintakereminder.data.repository.mapper.toProfileData
import com.example.waterintakereminder.data.local.entity.ProfileEntity
import com.example.waterintakereminder.presentation.ui.screens.profile.util.AchievementModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val waterIntakeRepository: WaterIntakeRepository,
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val _userAchievements = MutableStateFlow<List<AchievementModel>>(emptyList())
    val userAchievements: StateFlow<List<AchievementModel>> = _userAchievements.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ProfileNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadProfile()
        loadAchievements()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            try {
                val achievements = achievementRepository.getUnlockedAchievementsForUser(1)
                _userAchievements.value = achievements.map {
                    AchievementModel(
                        id = it.id,
                        title = it.title,
                        subtitle = it.description,
                        icon = it.icon
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _userAchievements.value = emptyList()
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            try {
                val profileEntity = profileRepository.getProfile()

                val intakes = waterIntakeRepository.getAllWaterIntakes().firstOrNull() ?: emptyList()
                val totalWater = intakes.sumOf { it.amountMl }
                val totalIntakes = intakes.size
                val averagePerDay = if (totalIntakes > 0) totalWater / totalIntakes else 0

                if (profileEntity != null) {
                    val updatedProfile = profileEntity.copy(
                        totalWaterConsumed = totalWater,
                        totalIntakes = totalIntakes,
                        averagePerDay = averagePerDay
                    )
                    profileRepository.updateProfile(updatedProfile)

                    val profileData = updatedProfile.toProfileData()
                    _uiState.value = ProfileUiState.Success(profileData)
                } else {
                    val currentTime = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
                    val defaultProfile = ProfileEntity(
                        id = 1,
                        userName = "Water Warrior",
                        joinDate = currentTime,
                        dailyGoal = 2000,
                        totalWaterConsumed = totalWater,
                        totalIntakes = totalIntakes,
                        averagePerDay = averagePerDay
                    )
                    profileRepository.insertProfile(defaultProfile)
                    _uiState.value = ProfileUiState.Success(defaultProfile.toProfileData())
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Failed to load profile.")
            }
        }
    }

    fun resetUiState() {
        loadProfile()
        loadAchievements()
    }
}