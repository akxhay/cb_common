package com.cb.cbtools.permission.presentation.composable.welcome

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.cb.cbtools.dynamic.DynamicConfig
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
    dynamicConfig: DynamicConfig
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = dynamicConfig.getWelcomeScreenBackgroundColor())
    ) {
        AppInfo(
            appIcon = appIcon,
            appName = appName,
            appDesc = appDesc,
            dynamicConfig = dynamicConfig
        )
        PermissionButton(
            appName = appName,
            context = context,
            visiblePermission = visiblePermission,
            currentPermission = currentPermission,
            modifier = Modifier
                .weight(10f),
            dynamicConfig = dynamicConfig

        )
        Footer(
            context = context,
            currentPermission = currentPermission,
            onclickSkip = onclickSkip,
            dynamicConfig = dynamicConfig
        )
    }
}

@Composable
fun AppInfo(
    @DrawableRes appIcon: Int,
    appName: String,
    appDesc: String,
    dynamicConfig: DynamicConfig
) {
    Column {
        WelcomeInfoSpacer()
        WelcomeInfoIcon(appIcon = appIcon)
        WelcomeInfoSpacer()
        WelcomeInfoText(
            text = appName,
            color = dynamicConfig.getWelcomeScreenTitleColor(),
            style = MaterialTheme.typography.displaySmall
        )
        WelcomeInfoSpacer()
        WelcomeInfoText(
            text = appDesc,
            color = dynamicConfig.getWelcomeScreenSummaryColor(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
