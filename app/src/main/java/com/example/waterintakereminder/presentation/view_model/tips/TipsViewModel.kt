package com.example.waterintakereminder.presentation.view_model.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.repository.TipRepository
import com.example.waterintakereminder.presentation.ui.screens.home.util.HydrationTipModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class TipsViewModel @Inject constructor(
    private val tipRepository: TipRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TipsUiState>(TipsUiState.Init)
    val uiState: StateFlow<TipsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TipsNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        seedTips()
        loadTips()
    }

    private fun seedTips() {
        viewModelScope.launch {
            try {
                tipRepository.seedTips()
            } catch (e: Exception) {
            }
        }
    }

    private fun loadTips() {
        viewModelScope.launch {
            _uiState.value = TipsUiState.Loading
            try {
                val tipEntities = tipRepository.getAllTips()
                val tips = tipEntities.map {
                    HydrationTipModel(
                        id = it.id,
                        title = it.title,
                        message = it.message,
                        icon = it.icon
                    )
                }
                _uiState.value = TipsUiState.Success(tips)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TipsUiState.Error(e.message ?: "Failed to load tips")
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _navigationEvent.send(TipsNavigationEvent.NavigateBack)
        }
    }

    fun resetUiState() {
        _uiState.value = TipsUiState.Init
        loadTips()
    }
}