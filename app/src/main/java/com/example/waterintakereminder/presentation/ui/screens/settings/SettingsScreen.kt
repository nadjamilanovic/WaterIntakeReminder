package com.example.waterintakereminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.ui.screens.settings.components.SettingsSwitchItem

@Composable
fun SettingsScreen(){
    var notificationsEnabled by remember { mutableStateOf(false)}
    var reminderEnabled by remember { mutableStateOf(false)}

    SettingsScreen(
        notificationsEnabled = notificationsEnabled,
        reminderEnabled = reminderEnabled,
        onNotificationsChange = { notificationsEnabled = it},
        onReminderChange = { reminderEnabled = it}
    )
}
@Composable
private fun SettingsScreen(
    notificationsEnabled: Boolean,
    reminderEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingsSwitchItem(
            title = "Enable Notifications",
            checked = notificationsEnabled,
            onCheckedChange = { onNotificationsChange(it) }
        )

        SettingsSwitchItem(
            title = "Enable Daily Reminder",
            checked = reminderEnabled,
            onCheckedChange = { onReminderChange(it) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}