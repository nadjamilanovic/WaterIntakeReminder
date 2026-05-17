package com.example.waterintakereminder.presentation.view_model.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.data.repository.WaterIntakeRepository
import com.example.waterintakereminder.data.repository.mapper.toWaterIntake
import com.example.waterintakereminder.data.repository.mapper.toWaterIntakeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.CancellationException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val waterIntakeRepository: WaterIntakeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<HomeNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _hydrationTip = MutableStateFlow("")
    val hydrationTip: StateFlow<String> = _hydrationTip.asStateFlow()

    init {
        loadIntakes()
        startHydrationTips()
    }

    private fun loadIntakes() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                waterIntakeRepository.getAllWaterIntakes().collect { intakes ->
                    val waterIntakes = intakes.map { it.toWaterIntake() }
                    _uiState.value = HomeUiState.Success(waterIntakes)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load intakes")
            }
        }
    }

    private fun observeHydrationTips(): kotlinx.coroutines.flow.Flow<String> = flow {
        val tips = listOf(
            "💧 Tip: Drink a glass of water right after waking up!",
            "💧 Tip: Keep a water bottle on your desk!",
            "💧 Tip: Add lemon or mint for better taste!",
            "💧 Tip: Set reminders every hour!",
            "💧 Tip: Drink water before each meal!"
        )
        for (tip in tips) {
            delay(3000L)
            emit(tip)
        }
    }

    private fun startHydrationTips() {
        viewModelScope.launch {
            observeHydrationTips().collectLatest { tip ->
                _hydrationTip.value = tip
            }
        }
    }

    fun addWater(amount: Int) {
        viewModelScope.launch {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            val newIntake = WaterIntakeEntity(
                amountMl = amount,
                timestamp = currentTime,
                isDrank = true
            )
            waterIntakeRepository.insertWaterIntake(newIntake)
            loadIntakes()
        }
    }

    fun toggleDrankStatus(id: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                val intake = currentState.intakes.find { it.id == id }
                intake?.let {
                    val updatedEntity = WaterIntakeEntity(
                        id = intake.id,
                        amountMl = intake.amountMl,
                        timestamp = intake.time,
                        isDrank = !intake.isDrank,
                        userId = 1
                    )
                    waterIntakeRepository.updateWaterIntake(updatedEntity)
                    loadIntakes()
                }
            }
        }
    }

    fun deleteIntake(id: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                val intake = currentState.intakes.find { it.id == id }
                intake?.let {
                    waterIntakeRepository.deleteWaterIntake(it.toWaterIntakeEntity())
                    loadIntakes()
                }
            }
        }
    }

    fun navigateToDetail(intake: WaterIntake) {
        viewModelScope.launch {
            _navigationEvent.send(HomeNavigationEvent.NavigateToDetail(intake))
        }
    }

    fun resetUiState() {
        loadIntakes()
    }
}