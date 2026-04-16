package com.example.waterintakereminder.presentation.ui.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val quickAmounts = listOf(100, 200, 250, 300, 500)

@Composable
fun QuickAddButtons(
    onQuickAdd: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(quickAmounts) { amount ->
            Button(
                onClick = { onQuickAdd(amount) },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text("+${amount}ml")
            }
        }
    }
}