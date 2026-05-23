package com.example.waterintakereminder.presentation.view_model.cloud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.repository.firebase.WaterIntakeFirestoreRepository
import com.example.waterintakereminder.presentation.view_model.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

@HiltViewModel
class CloudWaterIntakeViewModel @Inject constructor(
    private val repository: WaterIntakeFirestoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentUserId: String = ""

    fun setUserId(userId: String) {
        currentUserId = userId
        loadWaterIntakes()
        observeWaterIntakes()
    }

    private fun observeWaterIntakes() {
        viewModelScope.launch {
            repository.observeWaterIntakes(currentUserId).collect { intakes ->
                _uiState.value = HomeUiState.Success(intakes)
            }
        }
    }

    fun loadWaterIntakes() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = repository.getWaterIntakes(currentUserId)
                result.onSuccess { intakes ->
                    _uiState.value = HomeUiState.Success(intakes)
                }.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Failed to load intakes")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load intakes")
            }
        }
    }

    fun addWaterIntake(amountMl: Int, time: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = repository.addWaterIntake(amountMl, time, currentUserId)
                result.onSuccess {
                }.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Failed to add intake")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to add intake")
            }
        }
    }

    fun toggleWaterIntake(intakeId: String, isDrank: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleWaterIntake(intakeId, isDrank, currentUserId)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to update intake")
            }
        }
    }

    fun deleteWaterIntake(intakeId: String) {
        viewModelScope.launch {
            try {
                repository.deleteWaterIntake(intakeId, currentUserId)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to delete intake")
            }
        }
    }

    fun resetUiState() {
        loadWaterIntakes()
    }
}