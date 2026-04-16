package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.view_model.WaterViewModel
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterInputField
import com.example.waterintakereminder.presentation.ui.screens.home.component.WaterIntakeCard
import com.example.waterintakereminder.presentation.ui.screens.home.component.QuickAddButtons
import com.example.waterintakereminder.data.model.WaterIntake

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToList: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToTips: () -> Unit,
    onNavigateToInspiration: () -> Unit
) {
    val viewModel: WaterViewModel = viewModel()

    val intakes by viewModel.intakes.collectAsState()
    val totalWater by viewModel.totalWater.collectAsState()
    val dailyGoalProgress by viewModel.dailyGoalProgress.collectAsState()
    val remainingWater by viewModel.remainingWater.collectAsState()
    val waterCount by viewModel.waterCount.collectAsState()
    val averageAmount by viewModel.averageAmount.collectAsState()

    val onAddWater = { amount: Int -> viewModel.addWater(amount) }
    val onToggleDrank = { id: Int -> viewModel.toggleDrankStatus(id) }
    val onDeleteIntake = { id: Int -> viewModel.deleteIntake(id) }
    val onNavigateToDetail = { intakeId: Int ->
        navController.navigate(Screen.DetailWithArg.passId(intakeId))
    }

    HomeScreenStateless(
        intakes = intakes,
        totalWater = totalWater,
        dailyGoalProgress = dailyGoalProgress,
        remainingWater = remainingWater,
        waterCount = waterCount,
        averageAmount = averageAmount,
        onAddWater = onAddWater,
        onToggleDrank = onToggleDrank,
        onDeleteIntake = onDeleteIntake,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToProfile = onNavigateToProfile,
        onNavigateToTips = onNavigateToTips,
        onNavigateToInspiration = onNavigateToInspiration
    )
}

@Composable
private fun HomeScreenStateless(
    intakes: List<WaterIntake>,
    totalWater: Int,
    dailyGoalProgress: Float,
    remainingWater: Int,
    waterCount: Int,
    averageAmount: Int,
    onAddWater: (Int) -> Unit,
    onToggleDrank: (Int) -> Unit,
    onDeleteIntake: (Int) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToTips: () -> Unit,
    onNavigateToInspiration: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Water Intake Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        WaterInputField(onSubmit = onAddWater)

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
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
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
                        onClick = { onNavigateToDetail(intake.id) },
                        onToggle = { onToggleDrank(intake.id) },
                        onDelete = { onDeleteIntake(intake.id) }
                    )
                }
            }
        }
    }
}