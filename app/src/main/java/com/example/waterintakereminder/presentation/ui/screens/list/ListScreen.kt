package com.example.waterintakereminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.ui.screens.list.components.WaterIntakeListItem

@Composable
fun ListScreen(
    onItemClick: (Int) -> Unit
){
    val intakeList = remember {
        mutableStateListOf(
            250 to "08:00",
            300 to "10:30",
            200 to "13:15"
        )
    }
    ListScreen(
        intakeList = intakeList,
        onItemClick = onItemClick
    )
}
@Composable
private fun ListScreen(
    intakeList: List<Pair<Int, String>>,
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

        if (intakeList.isEmpty()) {
            Text(
                text = "No water intake yet",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(intakeList) { intake ->
                    WaterIntakeListItem(
                        amount = intake.first,
                        time = intake.second,
                        onClick = { onItemClick(intake.first) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListScreenPreview() {
    ListScreen(onItemClick = {})
}