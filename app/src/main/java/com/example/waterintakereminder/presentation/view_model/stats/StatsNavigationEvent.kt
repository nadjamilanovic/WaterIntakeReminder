package com.example.waterintakereminder.presentation.view_model.stats

sealed interface StatsNavigationEvent {
    data object NavigateBack : StatsNavigationEvent
}