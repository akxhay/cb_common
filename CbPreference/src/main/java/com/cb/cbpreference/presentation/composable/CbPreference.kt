package com.cb.cbpreference.presentation.composable

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.cb.cbpreference.presentation.composable.welcome.PreferenceScreen
import kotlinx.coroutines.delay

object CbPreference {
    @Composable
    fun SettingsScreen(
        context: Activity?,
        currentPermission: String,
        appName: String,
        appDesc: String,
        onclickSkip: () -> Unit,
        appNameColor: Color = Color.White,
        appDescColor: Color = Color.White,
        permissionCardBackground: Color = Color.White,
        permissionTextColor: Color = Color.Black,
        skipButtonColor: Color = Color.DarkGray,
        skipButtonTextColor: Color = Color.White,
        backgroundColor: Color = Color.DarkGray,
        navController: NavController
    ) {
        val visiblePermission = remember { mutableStateOf(false) }
        LaunchedEffect(visiblePermission) {
            visiblePermission.value = false
            delay(200) // to avoid repeated delays
            visiblePermission.value = true
        }

        PreferenceScreen(
            context = context!!,
            visiblePermission = visiblePermission,
            currentPermission = currentPermission,
            appName = appName,
            appDesc = appDesc,
            onclickSkip = onclickSkip,
            appNameColor = appNameColor,
            appDescColor = appDescColor,
            permissionCardBackground = permissionCardBackground,
            permissionTextColor = permissionTextColor,
            skipButtonColor = skipButtonColor,
            skipButtonTextColor = skipButtonTextColor,
            backgroundColor = backgroundColor,
            navController = navController
        )
    }
}