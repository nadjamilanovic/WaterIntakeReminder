package com.example.waterintakereminder.ui.screens.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterInputField(
    //input
    onSubmit: (Int) -> Unit) {

    //prebaci state unutar statefull screena
    var input by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                isError = false
            },
            label = { Text("Enter water amount (ml)") },
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        if (isError) {
            Text(
                text = "Please enter a valid number",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val amount = input.toIntOrNull()

                if (amount != null && amount > 0) {
                    onSubmit(amount)
                    input = ""
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Intake")
        }
    }
}