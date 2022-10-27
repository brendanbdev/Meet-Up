package com.example.thirdtry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = DarkestPurple,
    secondary = Purple,
    background = LightestPurple,
    surface = Color.White.copy(alpha = .85f),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkerPurple,
    onSurface = DarkestPurple.copy(alpha = 0.8f)
)

private val DarkColorPalette = darkColors(
    primary = Color.White,
    secondary = LighterPurple,
    background = DarkestPurple,
    surface = Color.White.copy(alpha = 0.15f),
    onPrimary = DarkestPurple,
    onSecondary = DarkestPurple,
    onBackground = Color.White,
    onSurface = Color.White.copy(alpha = .8f)
)

@Composable
fun ThirdTryTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}