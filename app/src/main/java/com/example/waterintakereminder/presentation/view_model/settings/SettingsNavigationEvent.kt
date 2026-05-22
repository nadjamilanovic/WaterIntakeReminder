package com.example.waterintakereminder.presentation.view_model.settings

sealed interface SettingsNavigationEvent {
    data object NavigateBack : SettingsNavigationEvent
}