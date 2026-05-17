package com.example.waterintakereminder.presentation.view_model.tips

import com.example.waterintakereminder.presentation.ui.screens.home.util.HydrationTipModel

sealed interface TipsUiState {
    data object Init : TipsUiState
    data object Loading : TipsUiState
    data class Success(val tips: List<HydrationTipModel>) : TipsUiState
    data class Error(val message: String) : TipsUiState
}