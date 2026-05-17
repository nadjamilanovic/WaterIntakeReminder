package com.example.waterintakereminder.presentation.view_model.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.repository.WaterIntakeRepository
import com.example.waterintakereminder.data.repository.mapper.toWaterIntake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class ListViewModel @Inject constructor(
    private val waterIntakeRepository: WaterIntakeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Init)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ListNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadIntakes()
    }

    private fun loadIntakes() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val intakeEntities = waterIntakeRepository.getAllWaterIntakes().firstOrNull() ?: emptyList()
                val intakes = intakeEntities.map { it.toWaterIntake() }
                _uiState.value = ListUiState.Success(intakes)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "Failed to load intakes")
            }
        }
    }

    fun onItemClick(amount: Int) {
        viewModelScope.launch {
            _navigationEvent.send(ListNavigationEvent.NavigateToDetail(amount))
        }
    }

    fun resetUiState() {
        _uiState.value = ListUiState.Init
        loadIntakes()
    }
}