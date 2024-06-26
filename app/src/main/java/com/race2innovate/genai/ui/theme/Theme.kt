package com.race2innovate.genai.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val darkColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = textColorDark,
    onTertiary = iconColorDark,
    onPrimary = humanMessageTextColorDark,
    onSecondary = botMessageTextColorDark,
    background = backgroundDark,
    outline = outlineDark,
    surface = humanMessageBackgroundDark,
    surfaceVariant = botMessageBackgroundDark,
    primaryContainer = appBarBackgroundDark
)

private val lightColorScheme = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = textColorLight,
    onTertiary = iconColorLight,
    onPrimary = humanMessageTextColorLight,
    onSecondary = botMessageTextColorLight,
    background = backgroundLight,
    outline = outlineLight,
    surface = humanMessageBackgroundLight,
    surfaceVariant = botMessageBackgroundLight,
    primaryContainer = appBarBackgroundLight
)

@Composable
fun GenAILLMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}