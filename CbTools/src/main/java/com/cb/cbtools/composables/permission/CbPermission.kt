package com.cb.cbtools.composables.permission

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.composables.permission.welcome.PermissionScreen
import com.cb.cbtools.dto.CbPermission
import kotlinx.coroutines.delay

object CbPermission {

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun WelcomeScreen(
        context: Activity?,
        @DrawableRes appIcon: Int,
        appName: String,
        appDesc: String,
        onclickSkip: () -> Unit,
        dynamicConfig: DynamicConfig,
        currentPermission: CbPermission
    ) {
        PermissionScreen(
            context = context!!,
            currentPermission = currentPermission,
            appIcon = appIcon,
            appName = appName,
            appDesc = appDesc,
            onclickSkip = onclickSkip,
            dynamicConfig = dynamicConfig
        )
    }
}