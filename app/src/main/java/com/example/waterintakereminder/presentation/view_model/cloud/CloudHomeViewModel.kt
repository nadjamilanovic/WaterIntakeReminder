package com.example.waterintakereminder.presentation.view_model.cloud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.data.repository.firebase.AuthRepository
import com.example.waterintakereminder.data.repository.firebase.WaterIntakeFirestoreRepository
import com.example.waterintakereminder.presentation.view_model.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CloudHomeViewModel @Inject constructor(
    private val firestoreRepository: WaterIntakeFirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _exportMessage = MutableStateFlow<String?>(null)
    val exportMessage: StateFlow<String?> = _exportMessage.asStateFlow()

    private val _exportError = MutableStateFlow<String?>(null)
    val exportError: StateFlow<String?> = _exportError.asStateFlow()

    private var currentUserId: String = ""

    fun setUserId(userId: String) {
        currentUserId = userId
        loadIntakesFromCloud()
        observeIntakes()
    }

    fun getCurrentUserId(): String? = authRepository.getCurrentUserId()

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = authRepository.signInWithGoogle(idToken)
                result.onSuccess { user ->
                    currentUserId = user.uid
                    loadIntakesFromCloud()
                    observeIntakes()
                }.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Google Sign-In failed")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Google Sign-In failed")
            }
        }
    }

    private fun observeIntakes() {
        viewModelScope.launch {
            firestoreRepository.observeWaterIntakes(currentUserId).collect { intakes ->
                _uiState.value = HomeUiState.Success(intakes)
            }
        }
    }

    fun loadIntakesFromCloud() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val result = firestoreRepository.getWaterIntakes(currentUserId)
                result.onSuccess { intakes ->
                    _uiState.value = HomeUiState.Success(intakes)
                }.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Failed to load intakes from cloud")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load intakes from cloud")
            }
        }
    }

    fun exportWaterIntakes() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                val result = firestoreRepository.exportWaterIntakes(currentState.intakes)
                result.onSuccess { fileUri ->
                    _exportMessage.value = "Exported to Downloads folder"
                }.onFailure { exception ->
                    _exportError.value = exception.message ?: "Export failed"
                }
            }
        }
    }

    fun clearExportState() {
        _exportMessage.value = null
        _exportError.value = null
    }

    fun createWaterIntake(amount: Int) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val result = firestoreRepository.addWaterIntake(amount, currentTime, currentUserId)

                result.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Failed to create intake")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to create intake")
            }
        }
    }

    fun toggleWaterIntake(intakeId: String, isDrank: Boolean) {
        viewModelScope.launch {
            try {
                firestoreRepository.toggleWaterIntake(intakeId, isDrank, currentUserId)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to update intake")
            }
        }
    }

    fun deleteWaterIntake(intakeId: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val result = firestoreRepository.deleteWaterIntake(intakeId, currentUserId)
                result.onFailure { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Failed to delete intake")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to delete intake")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            currentUserId = ""
            _uiState.value = HomeUiState.Init
        }
    }

    fun resetUiState() {
        loadIntakesFromCloud()
    }
}