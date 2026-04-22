package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.home.component.InspirationCard
import com.example.waterintakereminder.presentation.ui.screens.home.component.TitleSection
import com.example.waterintakereminder.presentation.ui.screens.home.util.InspirationModel
import com.example.waterintakereminder.presentation.ui.screens.home.util.getSampleInspirations

@Composable
fun HydrationInspirationScreen(
    modifier: Modifier = Modifier
) {
    val cards = getSampleInspirations()

    HydrationInspirationScreenStateless(
        cards = cards,
        modifier = modifier
    )
}

@Composable
private fun HydrationInspirationScreenStateless(
    cards: List<InspirationModel>,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TitleSection()
        }

        items(
            items = cards,
            key = { card -> card.id }
        ) { card ->
            InspirationCard(inspiration = card)
        }
    }
}