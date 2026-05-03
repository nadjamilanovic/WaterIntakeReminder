package com.example.waterintakereminder.presentation.view_model.home

import com.example.waterintakereminder.data.model.WaterIntake

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState
    data class Success(val intakes: List<WaterIntake>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}