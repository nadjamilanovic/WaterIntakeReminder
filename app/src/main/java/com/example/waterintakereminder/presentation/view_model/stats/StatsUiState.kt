package com.example.waterintakereminder.presentation.view_model.stats

import com.example.waterintakereminder.data.model.StatsData

sealed interface StatsUiState {
    data object Init : StatsUiState
    data object Loading : StatsUiState
    data class Success(val statsData: StatsData) : StatsUiState
    data class Error(val message: String) : StatsUiState
}