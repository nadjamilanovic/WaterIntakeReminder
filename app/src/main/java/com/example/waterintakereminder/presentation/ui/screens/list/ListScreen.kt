package com.example.waterintakereminder.presentation.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterintakereminder.presentation.ui.screens.list.component.WaterIntakeListItem
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar
import com.example.waterintakereminder.presentation.view_model.list.ListViewModel
import com.example.waterintakereminder.presentation.view_model.list.ListUiState
import com.example.waterintakereminder.presentation.view_model.list.ListNavigationEvent

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is ListNavigationEvent.NavigateToDetail -> {
                    onNavigateToDetail(event.amount)
                }
                else -> {}
            }
        }
    }

    when (uiState) {
        is ListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ListUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${(uiState as ListUiState.Error).message}")
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Retry")
                    }
                }
            }
        }
        is ListUiState.Success -> {
            val intakes = (uiState as ListUiState.Success).intakes
            ListScreenStateless(
                intakes = intakes,
                onItemClick = { viewModel.onItemClick(it) }
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
private fun ListScreenStateless(
    intakes: List<com.example.waterintakereminder.data.model.WaterIntake>,
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