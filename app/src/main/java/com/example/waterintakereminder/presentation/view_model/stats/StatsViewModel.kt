package com.example.waterintakereminder.presentation.view_model.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.model.StatsData
import com.example.waterintakereminder.data.repository.WaterIntakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.firstOrNull

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val waterIntakeRepository: WaterIntakeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatsUiState>(StatsUiState.Init)
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<StatsNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _uiState.value = StatsUiState.Loading
            try {
                val intakes = waterIntakeRepository.getAllWaterIntakes().firstOrNull() ?: emptyList()

                val totalWater = intakes.sumOf { it.amountMl }
                val totalDrinks = intakes.size
                val averageAmount = if (totalDrinks > 0) totalWater / totalDrinks else 0
                val dailyGoal = 2000
                val remainingWater = (dailyGoal - totalWater).coerceAtLeast(0)

                val statsData = StatsData(
                    totalWater = totalWater,
                    totalDrinks = totalDrinks,
                    averageAmount = averageAmount,
                    remainingWater = remainingWater
                )
                _uiState.value = StatsUiState.Success(statsData)
            } catch (e: Exception) {
                _uiState.value = StatsUiState.Error(e.message ?: "Failed to load stats")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = StatsUiState.Init
        loadStats()
    }
}