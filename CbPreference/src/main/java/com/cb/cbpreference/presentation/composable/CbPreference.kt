package com.cb.cbpreference.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.presentation.composable.preference.PreferenceScreenComposable
import kotlinx.coroutines.delay

object CbPreference {
    @Composable
    fun SettingsScreen(
        navController: NavController,
        preferencesScreen: PreferenceScreen
    ) {
        val visiblePermission = remember { mutableStateOf(false) }
        LaunchedEffect(visiblePermission) {
            visiblePermission.value = false
            delay(200) // to avoid repeated delays
            visiblePermission.value = true
        }
        PreferenceScreenComposable(
            preferencesScreen = preferencesScreen,
            navController = navController
        )
    }
}