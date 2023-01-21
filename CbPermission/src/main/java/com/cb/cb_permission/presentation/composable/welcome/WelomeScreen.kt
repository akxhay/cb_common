package com.cb.cb_permission.presentation.composable.welcome

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cb.cb_permission.presentation.composable.common.*
import com.cb.cbtools.permission.presentation.utils.PermissionButton

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionScreen(
    context: Activity,
    visiblePermission: MutableState<Boolean>,
    currentPermission: String,
    @DrawableRes appIcon: Int,
    appName: String,
    appDesc: String,
    onclickSkip: () -> Unit,
    appNameColor: Color,
    appDescColor: Color,
    permissionCardBackground: Color,
    permissionTextColor: Color,
    skipButtonColor: Color,
    skipButtonTextColor: Color,
    backgroundColor: Color
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        AppInfo(
            appIcon = appIcon,
            appName = appName,
            appNameColor = appNameColor,
            appDesc = appDesc,
            appDescColor = appDescColor
        )
        PermissionButton(
            appName = appName,
            context = context,
            visiblePermission = visiblePermission,
            currentPermission = currentPermission,
            modifier = Modifier
                .weight(10f),
            permissionCardBackground = permissionCardBackground,
            permissionTextColor = permissionTextColor
        )
        Footer(
            context = context,
            currentPermission = currentPermission,
            onclickSkip = onclickSkip,
            skipButtonColor = skipButtonColor,
            skipButtonTextColor = skipButtonTextColor
        )
    }
}

@Composable
fun AppInfo(
    @DrawableRes appIcon: Int,
    appName: String,
    appNameColor: Color,
    appDesc: String,
    appDescColor: Color
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        AppIconBox(appIcon = appIcon)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        AppNameBox(appName = appName, appNameColor = appNameColor)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        AppDescBox(appDesc = appDesc, appDescColor = appDescColor)
    }
}
