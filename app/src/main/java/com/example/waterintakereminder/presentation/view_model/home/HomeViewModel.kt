package com.example.waterintakereminder.presentation.view_model.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.presentation.ui.screens.home.util.getSampleIntakes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<HomeNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadIntakes()
    }

    private fun loadIntakes() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            kotlinx.coroutines.delay(500L)
            val intakes = getSampleIntakes()
            _uiState.value = HomeUiState.Success(intakes)
        }
    }

    fun addWater(amount: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                val currentIntakes = currentState.intakes
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val newId = (currentIntakes.maxOfOrNull { it.id } ?: 0) + 1
                val newIntake = WaterIntake(newId, amount, currentTime, true)
                _uiState.value = HomeUiState.Success(currentIntakes + newIntake)
            }
        }
    }

    fun toggleDrankStatus(id: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                val updatedIntakes = currentState.intakes.map {
                    if (it.id == id) it.copy(isDrank = !it.isDrank) else it
                }
                _uiState.value = HomeUiState.Success(updatedIntakes)
            }
        }
    }

    fun deleteIntake(id: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                _uiState.value = HomeUiState.Success(currentState.intakes.filter { it.id != id })
            }
        }
    }

    fun navigateToDetail(intake: WaterIntake) {
        viewModelScope.launch {
            _navigationEvent.send(HomeNavigationEvent.NavigateToDetail(intake))
        }
    }

    fun resetUiState() {
        _uiState.value = HomeUiState.Init
        loadIntakes()
    }
}