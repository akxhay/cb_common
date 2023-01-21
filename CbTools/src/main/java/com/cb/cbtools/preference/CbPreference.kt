package com.cb.cbtools.preference

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.preference.preference.PreferenceScreenComposable

object CbPreference {
    @Composable
    fun SettingsScreen(
        navController: NavController,
        activity: Activity,
        dynamicConfig: DynamicConfig
    ) {
        PreferenceScreenComposable(
            dynamicConfig = dynamicConfig,
            navController = navController,
            activity = activity
        )
    }
}