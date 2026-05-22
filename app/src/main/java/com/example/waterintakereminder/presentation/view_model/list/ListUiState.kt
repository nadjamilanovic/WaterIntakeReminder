package com.example.waterintakereminder.presentation.view_model.list

import com.example.waterintakereminder.data.model.WaterIntake

sealed interface ListUiState {
    data object Init : ListUiState
    data object Loading : ListUiState
    data class Success(val intakes: List<WaterIntake>) : ListUiState
    data class Error(val message: String) : ListUiState
}