package com.cb.cbtools.preference

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cb.cbtools.composables.CbAppBar
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.preference.preference.Settings

object CbPreference {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsScreen(
        navController: NavController,
        activity: Activity,
        dynamicConfig: DynamicConfig
    ) {
        Scaffold(
            topBar = {
                CbAppBar(
                    title = "Settings",
                    backAction = { navController.navigateUp() },
                    dynamicConfig = dynamicConfig
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .background(dynamicConfig.getBackgroundColor())
            ) {
                Settings(
                    dynamicConfig = dynamicConfig,
                    activity = activity
                )
            }
        }
    }
}