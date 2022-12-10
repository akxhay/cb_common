package com.cb.cbpreference.presentation.composable

import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.presentation.composable.preference.PreferenceScreenComposable
import kotlinx.coroutines.delay

object CbPreference {
    @Composable
    fun SettingsScreen(
        navController: NavController,
        preferencesScreen: PreferenceScreen,
        map: MutableState<HashMap<String, Any>>
    ) {
        val visiblePermission = remember { mutableStateOf(false) }
        LaunchedEffect(visiblePermission) {
            visiblePermission.value = false
            delay(200) // to avoid repeated delays
            visiblePermission.value = true
        }
        PreferenceScreenComposable(
            preferencesScreen = preferencesScreen,
            navController = navController,
            map = map
        )
    }
}