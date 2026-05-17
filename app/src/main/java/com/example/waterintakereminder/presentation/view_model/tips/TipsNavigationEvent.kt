package com.example.waterintakereminder.presentation.view_model.tips

sealed interface TipsNavigationEvent {
    data object NavigateBack : TipsNavigationEvent
}