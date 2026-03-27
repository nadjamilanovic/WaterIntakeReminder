package com.example.waterintakereminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.ui.screens.profile.components.ProfileInfoItem

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoItem(title = "Name", value = "Nadja")
        ProfileInfoItem(title = "Daily Goal", value = "2000 ml")

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoItem(title = "Total Intakes", value = "15")
        ProfileInfoItem(title = "Average per Day", value = "1800 ml")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}