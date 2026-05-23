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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterInputField
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterIntakeCard
import com.example.waterintakereminder.presentation.ui.screens.home.component.QuickAddButtons
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.derivedStateOf
import com.example.waterintakereminder.presentation.view_model.cloud.CloudHomeViewModel
import com.example.waterintakereminder.presentation.view_model.home.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloudHomeScreen(
    viewModel: CloudHomeViewModel = hiltViewModel(),
    navController: NavController,
    userId: String
) {
    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            viewModel.setUserId(userId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var inputText by rememberSaveable { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    val totalWater = (uiState as? HomeUiState.Success)?.intakes?.sumOf { it.amountMl } ?: 0
    val waterCount = (uiState as? HomeUiState.Success)?.intakes?.size ?: 0
    val averageAmount = if (waterCount > 0) totalWater / waterCount else 0

    val onWaterSubmit = {
        val amount = inputText.toIntOrNull()
        if (amount != null && amount > 0) {
            viewModel.createWaterIntake(amount)
            inputText = ""
            inputError = false
        } else {
            inputError = true
        }
    }

    when (uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${(uiState as HomeUiState.Error).message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Retry")
                    }
                }
            }
        }
        is HomeUiState.Success -> {
            val intakes = (uiState as HomeUiState.Success).intakes
            val dailyGoalProgress = (totalWater / 2000f).coerceAtMost(1f)
            val remainingWater = (2000 - totalWater).coerceAtLeast(0)

            CloudHomeScreenStateless(
                intakes = intakes,
                totalWater = totalWater,
                dailyGoalProgress = dailyGoalProgress,
                remainingWater = remainingWater,
                waterCount = waterCount,
                averageAmount = averageAmount,
                inputValue = inputText,
                inputError = inputError,
                onAddWater = { viewModel.createWaterIntake(it) },
                onToggleDrank = { id, isDrank -> viewModel.toggleWaterIntake(id.toString(), isDrank) },
                onDeleteIntake = { id -> viewModel.deleteWaterIntake(id.toString()) },
                onInputValueChange = { inputText = it },
                onWaterSubmit = onWaterSubmit,
                onNavigateToDetail = { intake ->
                    navController.navigate(Screen.DetailWithArg.passData(intake))
                }
            )
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Initializing...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CloudHomeScreenStateless(
    intakes: List<WaterIntake>,
    totalWater: Int,
    dailyGoalProgress: Float,
    remainingWater: Int,
    waterCount: Int,
    averageAmount: Int,
    inputValue: String,
    inputError: Boolean,
    onAddWater: (Int) -> Unit,
    onToggleDrank: (Int, Boolean) -> Unit,
    onDeleteIntake: (Int) -> Unit,
    onInputValueChange: (String) -> Unit,
    onWaterSubmit: () -> Unit,
    onNavigateToDetail: (WaterIntake) -> Unit,
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
            WaterAppBar(title = "Cloud Water Tracker")
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
                            onToggle = { onToggleDrank(intake.id, !intake.isDrank) },
                            onDelete = { onDeleteIntake(intake.id) }
                        )
                    }
                }
            }
        }
    }
}