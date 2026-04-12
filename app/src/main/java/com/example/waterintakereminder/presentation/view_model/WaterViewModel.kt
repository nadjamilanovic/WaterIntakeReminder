package com.example.waterintakereminder.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.data.model.HardcodedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WaterViewModel : ViewModel() {

    private val _intakes = MutableStateFlow<List<WaterIntake>>(emptyList())
    val intakes: StateFlow<List<WaterIntake>> = _intakes.asStateFlow()

    val totalWater: StateFlow<Int> = MutableStateFlow(0).apply {
        viewModelScope.launch {
            _intakes.collect { list ->
                value = list.sumOf { it.amountMl }
            }
        }
    }.asStateFlow()

    val dailyGoalProgress: StateFlow<Float> = MutableStateFlow(0f).apply {
        viewModelScope.launch {
            _intakes.collect { list ->
                val total = list.sumOf { it.amountMl }
                value = (total / 2000f).coerceAtMost(1f)
            }
        }
    }.asStateFlow()

    val remainingWater: StateFlow<Int> = MutableStateFlow(2000).apply {
        viewModelScope.launch {
            _intakes.collect { list ->
                val total = list.sumOf { it.amountMl }
                value = (2000 - total).coerceAtLeast(0)
            }
        }
    }.asStateFlow()

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        _intakes.value = HardcodedData.sampleIntakes
    }

    fun addWater(amountMl: Int) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val newId = (_intakes.value.maxOfOrNull { it.id } ?: 0) + 1
        val newIntake = WaterIntake(
            id = newId,
            amountMl = amountMl,
            time = currentTime,
            isDrank = true
        )
        _intakes.update { currentList -> currentList + newIntake }
    }

    fun toggleDrankStatus(id: Int) {
        _intakes.update { list ->
            list.map { if (it.id == id) it.copy(isDrank = !it.isDrank) else it }
        }
    }

    fun deleteIntake(id: Int) {
        _intakes.update { list ->
            list.filter { it.id != id }
        }
    }
}