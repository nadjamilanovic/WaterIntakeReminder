package com.example.waterintakereminder.presentation.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.list.component.WaterIntakeListItem
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.presentation.ui.screens.home.util.getSampleIntakes
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar

@Composable
fun ListScreen(
    onItemClick: (Int) -> Unit
) {
    var intakes by remember { mutableStateOf<List<WaterIntake>>(emptyList()) }

    LaunchedEffect(Unit) {
        intakes = getSampleIntakes()
    }

    ListScreenStateless(
        intakes = intakes,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreenStateless(
    intakes: List<WaterIntake>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WaterAppBar(title = "All Water Intakes")
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
}
