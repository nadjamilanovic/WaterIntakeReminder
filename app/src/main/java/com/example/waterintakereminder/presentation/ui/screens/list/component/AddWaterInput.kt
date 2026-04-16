package com.example.waterintakereminder.ui.screens.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddWaterInput(onAdd: (Int) -> Unit) {
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter water in ml") },
            isError = error,
            modifier = Modifier.fillMaxWidth()
        )
        if (error) {
            Text(
                text = "Please enter a valid amount",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val amount = input.toIntOrNull()
                if (amount != null && amount > 0) {
                    onAdd(amount)
                    input = ""
                    error = false
                } else {
                    error = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Water")
        }
    }
}