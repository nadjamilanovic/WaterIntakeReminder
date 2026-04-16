package com.example.waterintakereminder.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.settings.component.SettingsSwitchItem

@Composable
fun SettingsScreen() {
    var notificationsEnabled by rememberSaveable { mutableStateOf(false) }
    var reminderEnabled by rememberSaveable { mutableStateOf(false) }

    SettingsScreenStateless(
        notificationsEnabled = notificationsEnabled,
        reminderEnabled = reminderEnabled,
        onNotificationsChange = { notificationsEnabled = it },
        onReminderChange = { reminderEnabled = it }
    )
}

@Composable
private fun SettingsScreenStateless(
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
            text = "⚙️ Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                SettingsSwitchItem(
                    title = "🔔 Enable Notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = onNotificationsChange
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                SettingsSwitchItem(
                    title = "⏰ Enable Daily Reminder",
                    checked = reminderEnabled,
                    onCheckedChange = onReminderChange
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "ℹ️ About Reminders",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Daily reminders will help you stay hydrated throughout the day. You'll receive notifications at regular intervals.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}