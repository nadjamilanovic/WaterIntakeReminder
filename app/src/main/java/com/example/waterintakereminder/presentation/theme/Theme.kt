package com.example.waterintakereminder.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = Color.White,

    primaryContainer = AliceBlue,
    onPrimaryContainer = DeepTealDark,

    secondary = DustyOlive,
    onSecondary = Color.White,

    secondaryContainer = Thistle,
    onSecondaryContainer = DeepTealDark,

    background = AliceBlueLight,
    onBackground = DeepTealDark,

    surface = AliceBlueLight,
    onSurface = DeepTealDark,

    error = Color(0xFFB3261E),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = AliceBlue,
    onPrimary = DeepTealDark,

    primaryContainer = DeepTealDark,
    onPrimaryContainer = AliceBlueLight,

    secondary = DustyOliveLight,
    onSecondary = Color.White,

    secondaryContainer = RosyTaupeBeige,
    onSecondaryContainer = AliceBlueLight,

    background = Color(0xFF121212),
    onBackground = AliceBlueLight,

    surface = Color(0xFF1C1B1F),
    onSurface = AliceBlueLight,

    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)

@Composable
fun WaterIntakeReminderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}