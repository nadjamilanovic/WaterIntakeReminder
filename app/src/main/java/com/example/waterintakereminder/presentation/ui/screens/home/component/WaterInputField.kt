package com.example.waterintakereminder.presentation.ui.screens.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterInputField(
    onSubmit: (Int) -> Unit) {

    var input by rememberSaveable { mutableStateOf("") }
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