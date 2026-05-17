package com.example.waterintakereminder.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterintakereminder.presentation.ui.screens.settings.component.SettingsSwitchItem
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar
import com.example.waterintakereminder.presentation.view_model.settings.SettingsViewModel
import com.example.waterintakereminder.presentation.view_model.settings.SettingsUiState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is SettingsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is SettingsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${(uiState as SettingsUiState.Error).message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Retry")
                    }
                }
            }
        }
        is SettingsUiState.Success -> {
            val settings = (uiState as SettingsUiState.Success).settings

            SettingsScreenStateless(
                notificationsEnabled = settings.notificationsEnabled,
                reminderEnabled = settings.reminderEnabled,
                onNotificationsChange = { viewModel.updateNotifications(it) },
                onReminderChange = { viewModel.updateReminder(it) }
            )
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Initializing...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenStateless(
    notificationsEnabled: Boolean,
    reminderEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WaterAppBar(title = "Settings")
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

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
}