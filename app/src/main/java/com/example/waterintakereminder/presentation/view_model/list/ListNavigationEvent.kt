package com.example.waterintakereminder.presentation.view_model.list

sealed interface ListNavigationEvent {
    data class NavigateToDetail(val amount: Int) : ListNavigationEvent
    data object NavigateBack : ListNavigationEvent
}