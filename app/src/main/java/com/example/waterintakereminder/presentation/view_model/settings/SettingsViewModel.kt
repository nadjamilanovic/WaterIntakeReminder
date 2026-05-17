package com.example.waterintakereminder.presentation.view_model.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import com.example.waterintakereminder.data.model.SettingsData

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Init)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<SettingsNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadSettings()
        viewModelScope.launch {
            settingsRepository.insertDefaultSettings()
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            try {
                val settingsEntity = settingsRepository.getSettings()
                if (settingsEntity != null) {
                    val settings = SettingsData(
                        notificationsEnabled = settingsEntity.notificationsEnabled,
                        reminderEnabled = settingsEntity.reminderEnabled
                    )
                    _uiState.value = SettingsUiState.Success(settings)
                } else {
                    _uiState.value = SettingsUiState.Error("Settings not found")
                }
            } catch (e: Exception) {
                _uiState.value = SettingsUiState.Error(e.message ?: "Failed to load settings")
            }
        }
    }

    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val settingsEntity = settingsRepository.getSettings()
                settingsEntity?.let {
                    val updatedSettings = it.copy(notificationsEnabled = enabled)
                    settingsRepository.updateSettings(updatedSettings)
                    loadSettings()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateReminder(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val settingsEntity = settingsRepository.getSettings()
                settingsEntity?.let {
                    val updatedSettings = it.copy(reminderEnabled = enabled)
                    settingsRepository.updateSettings(updatedSettings)
                    loadSettings()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun resetUiState() {
        _uiState.value = SettingsUiState.Init
        loadSettings()
    }
}