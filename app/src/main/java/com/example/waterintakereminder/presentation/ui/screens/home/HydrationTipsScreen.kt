package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.home.component.HydrationTipCard
import com.example.waterintakereminder.presentation.ui.screens.home.util.HydrationTipModel
import com.example.waterintakereminder.presentation.ui.screens.home.util.getSampleTips

@Composable
fun HydrationTipsScreen(
    modifier: Modifier = Modifier
) {
    val tips = getSampleTips()

    HydrationTipsScreenStateless(
        tips = tips,
        modifier = modifier
    )
}

@Composable
private fun HydrationTipsScreenStateless(
    tips: List<HydrationTipModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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