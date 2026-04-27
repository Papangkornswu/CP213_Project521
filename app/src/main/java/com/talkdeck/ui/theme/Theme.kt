package com.talkdeck.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ModernDarkColorScheme = darkColorScheme(
    primary = ModernBlue,
    secondary = ModernSurface,
    tertiary = ModernLightBlue,
    background = ModernDarkGray,
    surface = ModernSurface,
    onPrimary = ModernWhite,
    onSecondary = ModernWhite,
    onTertiary = ModernBlack,
    onBackground = ModernWhite,
    onSurface = ModernWhite,
)

private val ModernLightColorScheme = lightColorScheme(
    primary = ModernBlue,
    secondary = ModernWhite,
    tertiary = ModernLightBlue,
    background = Color(0xFFF2F2F7),
    surface = ModernWhite,
    onPrimary = ModernWhite,
    onSecondary = ModernBlack,
    onTertiary = ModernWhite,
    onBackground = ModernBlack,
    onSurface = ModernBlack,
)

@Composable
fun TalkDeckTheme(
    // We force the Modern look to prefer Dark Mode aesthetics for the "Modern Black, Blue, White" feel you wanted.
    darkTheme: Boolean = true, // isSystemInDarkTheme() 
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ModernDarkColorScheme else ModernLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
