package com.cb.cb_permission.presentation.composable

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.cb.cb_permission.presentation.composable.welcome.PermissionScreen
import kotlinx.coroutines.delay

object CbPermission {

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun WelcomeScreen(
        context: Activity?,
        currentPermission: String,
        @DrawableRes appIcon: Int,
        appName: String,
        appDesc: String,
        onclickSkip: () -> Unit,
        appNameColor: Color = Color.White,
        appDescColor: Color = Color.White,
        permissionCardBackground: Color = Color.White,
        permissionTextColor: Color = Color.Black,
        skipButtonColor: Color = Color.DarkGray,
        skipButtonTextColor: Color = Color.White,
        backgroundColor: Color = Color.DarkGray
    ) {
        val visiblePermission = remember { mutableStateOf(false) }
        LaunchedEffect(visiblePermission) {
            visiblePermission.value = false
            delay(200) // to avoid repeated delays
            visiblePermission.value = true
        }

        PermissionScreen(
            context = context!!,
            visiblePermission = visiblePermission,
            currentPermission = currentPermission,
            appIcon = appIcon,
            appName = appName,
            appDesc = appDesc,
            onclickSkip = onclickSkip,
            appNameColor = appNameColor,
            appDescColor = appDescColor,
            permissionCardBackground = permissionCardBackground,
            permissionTextColor = permissionTextColor,
            skipButtonColor = skipButtonColor,
            skipButtonTextColor = skipButtonTextColor,
            backgroundColor = backgroundColor
        )
    }
}