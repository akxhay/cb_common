package com.cb.cbtools.preference

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.cb.cbtools.customise.data.PreferenceScreen
import com.cb.cbtools.preference.preference.PreferenceScreenComposable

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