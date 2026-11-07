package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val FireColorScheme = darkColorScheme(
    primary = FireOrange,
    onPrimary = FireDarkBg,
    secondary = FireBlue,
    onSecondary = FireTextPrimary,
    background = FireDarkBg,
    onBackground = FireTextPrimary,
    surface = FireDarkSurface,
    onSurface = FireTextPrimary,
    surfaceVariant = FireDarkSurfaceElevated,
    onSurfaceVariant = FireTextSecondary
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = FireColorScheme,
        typography = Typography,
        content = content
    )
}
