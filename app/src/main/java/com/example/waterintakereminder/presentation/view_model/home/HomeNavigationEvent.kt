package com.example.waterintakereminder.presentation.view_model.home

sealed interface HomeNavigationEvent {
    data class NavigateToDetail(val intake: WaterIntake) : HomeNavigationEvent
}