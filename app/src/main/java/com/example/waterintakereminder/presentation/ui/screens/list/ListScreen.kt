package com.example.waterintakereminder.presentation.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterintakereminder.presentation.view_model.WaterViewModel
import com.example.waterintakereminder.presentation.ui.screens.list.component.WaterIntakeListItem
import com.example.waterintakereminder.data.model.WaterIntake

@Composable
fun ListScreen(
    onItemClick: (Int) -> Unit
) {
    val viewModel: WaterViewModel = viewModel()
    val intakes by viewModel.intakes.collectAsState()

    ListScreenStateless(
        intakes = intakes,
        onItemClick = onItemClick
    )
}

@Composable
private fun ListScreenStateless(
    intakes: List<WaterIntake>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Water Intakes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (intakes.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💧",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No water intake yet",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Add your first glass from the home screen!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(intakes) { intake ->
                    WaterIntakeListItem(
                        id = intake.id,
                        amount = intake.amountMl,
                        time = intake.time,
                        isDrank = intake.isDrank,
                        onClick = { onItemClick(intake.amountMl) }
                    )
                }
            }
        }
    }
}