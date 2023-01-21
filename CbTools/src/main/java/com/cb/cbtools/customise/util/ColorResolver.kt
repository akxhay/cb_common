package com.cb.cbtools.customise.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.cb.cbtools.customise.data.PreferenceColor

object ColorResolver {

    @Composable
    fun getColor(color: PreferenceColor?, defaultColor: Color): Color {
        color?.let {
            return when (color.type) {
                "hex" -> getHexColor(color.value, defaultColor)
                "colorScheme" -> getMaterialColor(color.value, defaultColor)
                "default" -> getDefaultColor(color.value, defaultColor)
                else -> {
                    defaultColor
                }
            }
        }
        return defaultColor
    }

    private fun getDefaultColor(colorString: String?, defaultColor: Color): Color {
        colorString?.let {
            return when (colorString) {
                "Black" -> Color.Black
                "DarkGray" -> Color.DarkGray
                "Gray" -> Color.Gray
                "LightGray" -> Color.LightGray
                "White" -> Color.White
                "Red" -> Color.Red
                "Green" -> Color.Green
                "Blue" -> Color.Blue
                "Yellow" -> Color.Yellow
                "Cyan" -> Color.Cyan
                "Magenta" -> Color.Magenta
                "Transparent" -> Color.Transparent

                else -> {
                    defaultColor
                }
            }
        }
        return defaultColor
    }

    @Composable
    private fun getMaterialColor(colorString: String?, defaultColor: Color): Color {
        colorString?.let {
            return when (colorString) {
                "primary" -> MaterialTheme.colorScheme.primary
                "onPrimary" -> MaterialTheme.colorScheme.onPrimary
                "primaryContainer" -> MaterialTheme.colorScheme.primaryContainer
                "onPrimaryContainer" -> MaterialTheme.colorScheme.onPrimaryContainer
                "inversePrimary" -> MaterialTheme.colorScheme.inversePrimary
                "secondary" -> MaterialTheme.colorScheme.secondary
                "onSecondary" -> MaterialTheme.colorScheme.onSecondary
                "secondaryContainer" -> MaterialTheme.colorScheme.secondaryContainer
                "onSecondaryContainer" -> MaterialTheme.colorScheme.onSecondaryContainer
                "tertiary" -> MaterialTheme.colorScheme.tertiary
                "onTertiary" -> MaterialTheme.colorScheme.onTertiary
                "tertiaryContainer" -> MaterialTheme.colorScheme.tertiaryContainer
                "onTertiaryContainer" -> MaterialTheme.colorScheme.onTertiaryContainer
                "background" -> MaterialTheme.colorScheme.background
                "onBackground" -> MaterialTheme.colorScheme.onBackground
                "surface" -> MaterialTheme.colorScheme.surface
                "onSurface" -> MaterialTheme.colorScheme.onSurface
                "surfaceVariant" -> MaterialTheme.colorScheme.surfaceVariant
                "onSurfaceVariant" -> MaterialTheme.colorScheme.onSurfaceVariant
                "surfaceTint" -> MaterialTheme.colorScheme.surfaceTint
                "inverseSurface" -> MaterialTheme.colorScheme.inverseSurface
                "inverseOnSurface" -> MaterialTheme.colorScheme.inverseOnSurface
                "error" -> MaterialTheme.colorScheme.error
                "onError" -> MaterialTheme.colorScheme.onError
                "errorContainer" -> MaterialTheme.colorScheme.errorContainer
                "onErrorContainer" -> MaterialTheme.colorScheme.onErrorContainer
                "outline" -> MaterialTheme.colorScheme.outline
                "outlineVariant" -> MaterialTheme.colorScheme.outlineVariant
                "scrim" -> MaterialTheme.colorScheme.scrim
                else -> {
                    defaultColor
                }
            }
        }
        return defaultColor
    }

    private fun getHexColor(colorString: String?, defaultColor: Color): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }
}