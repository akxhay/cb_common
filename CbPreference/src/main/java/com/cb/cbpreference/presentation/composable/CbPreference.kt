package com.cb.cbpreference.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.presentation.composable.preference.PreferenceScreen
import kotlinx.coroutines.delay

object CbPreference {
    @Composable
    fun SettingsScreen(
        appName: String = "Application",
        titleColor: Color = Color.Black,
        headerColor: Color = Color.Black,
        summaryColor: Color = Color.Gray,
        dividerColor: Color = Color.Green,
        iconColor: Color = Color.Green,
        backgroundColor: Color = Color.White,
        appbarTitleColor: Color = Color.Black,
        appbarBackgroundColor: Color = Color.White,
        navController: NavController,
        preferencesScreen: PreferenceScreen
    ) {
        val visiblePermission = remember { mutableStateOf(false) }
        LaunchedEffect(visiblePermission) {
            visiblePermission.value = false
            delay(200) // to avoid repeated delays
            visiblePermission.value = true
        }

        PreferenceScreen(
            appName = appName,
            titleColor = titleColor,
            headerColor = headerColor,
            summaryColor = summaryColor,
            dividerColor = dividerColor,
            iconColor = iconColor,
            backgroundColor = backgroundColor,
            appbarTitleColor = appbarTitleColor,
            appbarBackgroundColor = appbarBackgroundColor,
            navController = navController,
            preferencesScreen = preferencesScreen
        )
    }
}