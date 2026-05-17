package com.example.waterintakereminder.presentation.view_model.settings

import com.example.waterintakereminder.data.model.SettingsData

sealed interface SettingsUiState {
    data object Init : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(val settings: SettingsData) : SettingsUiState
    data class Error(val message: String) : SettingsUiState
}