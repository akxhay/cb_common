package com.cb.cbtools.permission.presentation.composable

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.permission.presentation.composable.welcome.PermissionScreen
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
        dynamicConfig: DynamicConfig
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
            dynamicConfig = dynamicConfig
        )
    }
}