package com.zagirlek.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    onSurface = Black,
    surface = White,
    surfaceVariant = LightGray,
    onSurfaceVariant = DarkGray,

    background = White,
    onBackground = Black,

    onPrimary = White,
    primary = Black
)
private val DarkColorScheme = darkColorScheme(
    onSurface = White,
    surface = Black,
    surfaceVariant = DarkGray,
    onSurfaceVariant = LightGray,

    background = Black,
    onBackground = White,

    onPrimary = Black,
    primary = White
)

@Composable
fun NyTimesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}