package com.cb.cbcommon.presentation.theme

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

private val darkColorScheme = darkColorScheme(
    primary = Color(0xFF282D33),
    onPrimary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    inversePrimary = Color(0xFF1E2126),
    secondary = Color(0xFFFD971D),
    onSecondary = Color.Black,
    onPrimaryContainer = Color.White,
    surface = Color(0xFF1E2126)
//    tertiary = Pink80
)

private val lightColorScheme = lightColorScheme(
    primary = Color(android.graphics.Color.parseColor("#3478f5")),
    onPrimary = Color.White,
    background = Color.White,
    inversePrimary = Color(android.graphics.Color.parseColor("#3478f5")),
    secondary = Color(android.graphics.Color.parseColor("#e5e4ea")),
    onSecondary = Color(android.graphics.Color.parseColor("#1f2227")),
    onPrimaryContainer = Color(android.graphics.Color.parseColor("#3478f5")),

//    secondary = PurpleGrey40,
//    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun CbCommonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}