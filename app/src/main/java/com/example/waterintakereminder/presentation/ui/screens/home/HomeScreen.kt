package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterInputField
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterIntakeCard
import com.example.waterintakereminder.presentation.ui.screens.home.component.QuickAddButtons
import com.example.waterintakereminder.data.model.WaterIntake
import java.text.SimpleDateFormat
import java.util.*
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.derivedStateOf

@Composable
fun HomeScreen(
    navController: NavController,
    intakes: List<WaterIntake>,
    onUpdateIntakes: (List<WaterIntake>) -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    val totalWater = intakes.sumOf { it.amountMl }
    val dailyGoalProgress = (totalWater / 2000f).coerceAtMost(1f)
    val remainingWater = (2000 - totalWater).coerceAtLeast(0)
    val waterCount = intakes.size
    val averageAmount = if (intakes.isNotEmpty()) intakes.sumOf { it.amountMl } / intakes.size else 0

    val onAddWater = { amount: Int ->
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val newId = (intakes.maxOfOrNull { it.id } ?: 0) + 1
        val newIntake = WaterIntake(newId, amount, currentTime, true)
        onUpdateIntakes(intakes + newIntake)
    }

    val onToggleDrank = { id: Int ->
        val updatedIntakes = intakes.map {
            if (it.id == id) it.copy(isDrank = !it.isDrank) else it
        }
        onUpdateIntakes(updatedIntakes)
    }

    val onDeleteIntake = { id: Int ->
        onUpdateIntakes(intakes.filter { it.id != id })
    }

    val onNavigateToDetail = { intake: WaterIntake ->
        navController.navigate(Screen.DetailWithArg.passData(intake))
    }

    val onWaterSubmit = {
        val amount = inputText.toIntOrNull()
        if (amount != null && amount > 0) {
            onAddWater(amount)
            inputText = ""
            inputError = false
        } else {
            inputError = true
        }
    }

    HomeScreenStateless(
        intakes = intakes,
        totalWater = totalWater,
        dailyGoalProgress = dailyGoalProgress,
        remainingWater = remainingWater,
        waterCount = waterCount,
        averageAmount = averageAmount,
        inputValue = inputText,
        inputError = inputError,
        onAddWater = onAddWater,
        onToggleDrank = onToggleDrank,
        onDeleteIntake = onDeleteIntake,
        onNavigateToDetail = onNavigateToDetail,
        onInputValueChange = { inputText = it },
        onWaterSubmit = onWaterSubmit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenStateless(
    intakes: List<WaterIntake>,
    totalWater: Int,
    dailyGoalProgress: Float,
    remainingWater: Int,
    waterCount: Int,
    averageAmount: Int,
    inputValue: String,
    inputError: Boolean,
    onAddWater: (Int) -> Unit,
    onToggleDrank: (Int) -> Unit,
    onDeleteIntake: (Int) -> Unit,
    onNavigateToDetail: (WaterIntake) -> Unit,
    onInputValueChange: (String) -> Unit,
    onWaterSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showScrollToTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        topBar = {
            WaterAppBar(title = "Water Tracker")
        },
        floatingActionButton = {
            if (showScrollToTop) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.size(56.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "↑",
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Today's Progress", style = MaterialTheme.typography.titleMedium)
                    Text("$totalWater / 2000 ml", style = MaterialTheme.typography.headlineSmall)
                    LinearProgressIndicator(
                        progress = { dailyGoalProgress },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Remaining: $remainingWater ml", style = MaterialTheme.typography.bodySmall)
                    if (totalWater >= 2000) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "🎉 CONGRATULATIONS! 🎉",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when {
                totalWater == 0 -> Text("💪 Start your hydration journey!", style = MaterialTheme.typography.bodyMedium)
                totalWater < 1000 -> Text("🥤 You're getting there, keep going!", style = MaterialTheme.typography.bodyMedium)
                totalWater < 2000 -> Text("🌟 Great progress! Almost at goal!", style = MaterialTheme.typography.bodyMedium)
                else -> Text("🏆 Perfect! You've hit your goal!", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("🥤 Drinks: $waterCount", style = MaterialTheme.typography.bodyMedium)
                Text("📊 Avg: $averageAmount ml", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            QuickAddButtons(onQuickAdd = onAddWater)

            Spacer(modifier = Modifier.height(16.dp))

            WaterInputField(
                value = inputValue,
                isError = inputError,
                onValueChange = onInputValueChange,
                onSubmit = onWaterSubmit
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (intakes.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = "💧 No water intake yet.\nTap + to add your first glass!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = "Today's Intakes",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(intakes) { intake ->
                        WaterIntakeCard(
                            id = intake.id,
                            amount = intake.amountMl,
                            time = intake.time,
                            isDrank = intake.isDrank,
                            onClick = { onNavigateToDetail(intake) },
                            onToggle = { onToggleDrank(intake.id) },
                            onDelete = { onDeleteIntake(intake.id) }
                        )
                    }
                }
            }
        }
    }
}