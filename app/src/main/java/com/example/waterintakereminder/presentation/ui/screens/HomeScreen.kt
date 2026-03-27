package com.example.waterintakereminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.waterintakereminder.ui.screens.home.components.AddWaterInput
import com.example.waterintakereminder.ui.screens.home.components.WaterIntakeCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val intakeList = remember { mutableStateListOf<Pair<Int, String>>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Water Intake Tracker ",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddWaterInput(onAdd = { amount ->
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            intakeList.add(amount to currentTime)
        })

        Spacer(modifier = Modifier.height(16.dp))

        if (intakeList.isEmpty()) {
            Text(
                text = "No water intake yet",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(intakeList) { intake ->
                    WaterIntakeCard(
                        amount = intake.first,
                        time = intake.second
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}