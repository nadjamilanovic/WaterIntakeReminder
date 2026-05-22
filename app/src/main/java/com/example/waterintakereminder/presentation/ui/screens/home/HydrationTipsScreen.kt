package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.waterintakereminder.presentation.ui.screens.home.component.HydrationTipCard
import com.example.waterintakereminder.presentation.ui.screens.home.util.HydrationTipModel
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar
import com.example.waterintakereminder.presentation.view_model.tips.TipsViewModel
import com.example.waterintakereminder.presentation.view_model.tips.TipsUiState
import com.example.waterintakereminder.presentation.view_model.tips.TipsNavigationEvent

@Composable
fun HydrationTipsScreen(
    viewModel: TipsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                TipsNavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when (uiState) {
        is TipsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TipsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${(uiState as TipsUiState.Error).message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Retry")
                    }
                }
            }
        }
        is TipsUiState.Success -> {
            val tips = (uiState as TipsUiState.Success).tips
            HydrationTipsScreenStateless(
                tips = tips,
                onBackClick = { viewModel.onBackClick() }
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
private fun HydrationTipsScreenStateless(
    tips: List<HydrationTipModel>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WaterAppBar(
                title = "Hydration Tips",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "💧 Hydration Tips & Motivation",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Scroll horizontally to see more tips!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(
                    items = tips,
                    key = { tip -> tip.id }
                ) { tip ->
                    HydrationTipCard(tip = tip)
                }
            }
        }
    }
}