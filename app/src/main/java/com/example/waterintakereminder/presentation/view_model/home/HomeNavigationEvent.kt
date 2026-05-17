package com.example.waterintakereminder.presentation.view_model.home
import com.example.waterintakereminder.data.model.WaterIntake
sealed interface HomeNavigationEvent {
    data class NavigateToDetail(val intake: WaterIntake) : HomeNavigationEvent
}