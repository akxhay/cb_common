package com.cb.cbcommon

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.cb.cbtools.dynamic.data.DynamicApp
import com.cb.cbtools.dynamic.data.PreferenceCategory
import com.cb.cbtools.dynamic.util.ColorResolver
import com.cb.cbtools.dynamic.util.buildDynamicApp

class DynamicConfig(private val context: Context) {
    private lateinit var dynamicApp: DynamicApp

    init {
        Log.d("DynamicConfig", "init ")
        updateConfig()
    }

    private fun updateConfig() {
        dynamicApp = buildDynamicApp(context = context)
        Log.d("DynamicConfig", "dynamicApp : $dynamicApp")
    }

    fun getDynamicApp(): DynamicApp {
        return dynamicApp
    }

    fun getAppName(): String {
        return dynamicApp.appName
    }

    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(dynamicApp.sharedPrefName, Context.MODE_PRIVATE)
    }


    fun getPreferenceCategories(): List<PreferenceCategory>? {
        return dynamicApp.preferences
    }

    @Composable
    fun getWelcomeScreenBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenBackgroundColor,
            MaterialTheme.colorScheme.surface
        )
    }

    @Composable
    fun getWelcomeScreenTitleColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenTitleColor,
            MaterialTheme.colorScheme.onSurface
        )
    }

    @Composable
    fun getWelcomeScreenSummaryColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenSummaryColor,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    @Composable
    fun getWelcomeScreenCardBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenCardBackgroundColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getWelcomeScreenCardContentColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenCardContentColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getWelcomeScreenSkipTextColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.welcomeScreenSkipTextColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getAppBarBackGroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.appbarBackgroundColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getAppBarTitleColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.appbarTitleColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getAppBarMenuIconColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.appBarMenuIconColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getAppBarMenuTextColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.appBarMenuTextColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getFloatingBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.floatingBackgroundColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getFloatingContentColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.floatingContentColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }


    @Composable
    fun getBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.backgroundColor,
            MaterialTheme.colorScheme.background
        )
    }

    @Composable
    fun getPrimaryTextOnBackGroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.primaryTextOnBackgroundColor,
            MaterialTheme.colorScheme.onBackground
        )
    }

    @Composable
    fun getSecondaryTextOnBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.secondaryTextOnBackgroundColor,
            MaterialTheme.colorScheme.onBackground
        )
    }

    @Composable
    fun getIconTintOnBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.iconTintOnBackgroundColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getCardColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.cardColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getCardBorderColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.cardBorderColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getCardShadowColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.cardShadowColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getPrimaryTextOnCardColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.primaryTextOnCardColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getSecondaryTextOnCardColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.secondaryTextOnCardColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getRadioCheckedColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.radioCheckedColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getRadioUnCheckedColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.radioUnCheckedColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getDividerColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.dividerColor,
            Color.Transparent
        )
    }

    @Composable
    fun getAlertBackgroundColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertBackgroundColor,
           MaterialTheme.colorScheme.surface
        )
    }

    @Composable
    fun getAlertContentColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertContentColor,
            MaterialTheme.colorScheme.onSurface
        )
    }

    @Composable
    fun getAlertTitleColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertTitleColor,
            MaterialTheme.colorScheme.onSurface
        )
    }

    @Composable
    fun getAlertConfirmButtonColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertConfirmButtonColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getAlertConfirmTextColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertConfirmTextColor,
            MaterialTheme.colorScheme.onPrimary
        )
    }

    @Composable
    fun getAlertDismissTextColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertDismissTextColor,
            MaterialTheme.colorScheme.primary
        )
    }

    @Composable
    fun getAlertDividerColor(): Color {
        return ColorResolver.getColor(
            color = dynamicApp.style.alertDividerColor,
            MaterialTheme.colorScheme.primary
        )
    }

}