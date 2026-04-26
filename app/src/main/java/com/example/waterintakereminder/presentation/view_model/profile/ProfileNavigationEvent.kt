package com.example.waterintakereminder.presentation.view_model.profile

sealed interface ProfileNavigationEvent {
    data object Navigate : ProfileNavigationEvent
    data object NavigateBack : ProfileNavigationEvent
}