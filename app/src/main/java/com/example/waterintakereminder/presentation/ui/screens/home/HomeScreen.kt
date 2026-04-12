package com.example.waterintakereminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterintakereminder.presentation.view_model.WaterViewModel
import com.example.waterintakereminder.ui.screens.home.components.AddWaterInput
import com.example.waterintakereminder.ui.screens.home.components.WaterIntakeCard
import com.example.waterintakereminder.ui.screens.home.components.QuickAddButtons
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: WaterViewModel = viewModel()
) {
    val intakes by viewModel.intakes.collectAsState()
    val totalWater by viewModel.totalWater.collectAsState()
    val dailyGoalProgress by viewModel.dailyGoalProgress.collectAsState()
    val remainingWater by viewModel.remainingWater.collectAsState()

    HomeScreenContent(
        intakes = intakes,
        totalWater = totalWater,
        dailyGoalProgress = dailyGoalProgress,
        remainingWater = remainingWater,
        onAddWater = { amount -> viewModel.addWater(amount) },
        onToggleDrank = { id -> viewModel.toggleDrankStatus(id) },
        onDeleteIntake = { id -> viewModel.deleteIntake(id) },
        onNavigateToDetail = { intakeId ->
            navController.navigate("detail/$intakeId")
        }
    )
}

@Composable
private fun HomeScreenContent(
    intakes: List<com.example.waterintakereminder.data.model.WaterIntake>,
    totalWater: Int,
    dailyGoalProgress: Float,
    remainingWater: Int,
    onAddWater: (Int) -> Unit,
    onToggleDrank: (Int) -> Unit,
    onDeleteIntake: (Int) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
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
                    progress = dailyGoalProgress,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Remaining: $remainingWater ml", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        QuickAddButtons(onQuickAdd = onAddWater)

        Spacer(modifier = Modifier.height(16.dp))

        AddWaterInput(onAdd = onAddWater)

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
                items(intakes) { intake ->
                    WaterIntakeCard(
                        id = intake.id,
                        amount = intake.amountMl,
                        time = intake.time,
                        isDrank = intake.isDrank,
                        onClick = { id -> onNavigateToDetail(id) },
                        onToggle = { id -> onToggleDrank(id) },
                        onDelete = { id -> onDeleteIntake(id) }
                    )
                }
            }
        }
    }
}