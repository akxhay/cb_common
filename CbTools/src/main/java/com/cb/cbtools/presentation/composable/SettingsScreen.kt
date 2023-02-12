package com.cb.cbtools.presentation.composable.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.preference.CbPreference


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    activity: Activity,
    dynamicConfig: DynamicConfig
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CbPreference.SettingsScreen(
            navController = navController,
            activity = activity,
            dynamicConfig = dynamicConfig
        )
    }
}




