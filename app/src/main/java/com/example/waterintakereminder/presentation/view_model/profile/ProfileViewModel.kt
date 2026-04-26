package com.example.waterintakereminder.presentation.view_model.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.model.ProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ProfileNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            kotlinx.coroutines.delay(500L)

            val profileData = ProfileData(
                userName = "Water Warrior",
                email = "waterwarrior@example.com",
                joinDate = "January 15, 2025",
                dailyGoal = 2000,
                totalWaterConsumed = 1250,
                totalIntakes = 5,
                averagePerDay = 250
            )

            _uiState.value = ProfileUiState.Success(profileData)
        }
    }

    fun resetUiState() {
        _uiState.value = ProfileUiState.Init
    }
}