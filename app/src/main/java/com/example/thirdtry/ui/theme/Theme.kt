package com.example.thirdtry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = Black,
    background = LighterGreen,
    surface = LightGreen,
    onPrimary = White,
    onBackground = Black,
    onSurface = Black
)

private val DarkColorPalette = darkColors(
    primary = White,
    background = DarkestGreen,
    surface = DarkerGreen,
    onPrimary = DarkestGreen,
    onBackground = White,
    onSurface = White
)

@Composable
fun MeetUpTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = colors.surface
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}