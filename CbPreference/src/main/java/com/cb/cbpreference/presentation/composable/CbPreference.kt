package com.cb.cbpreference.presentation.composable

import android.app.Activity
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
        activity: Activity
    ) {
        PreferenceScreenComposable(
            preferencesScreen = preferencesScreen,
            navController = navController,
            activity = activity
        )
    }
}