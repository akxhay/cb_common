package com.cb.cb_permission.ui

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cb.cb_permission.ui.ComposableItems.AppDescBox
import com.cb.cb_permission.ui.ComposableItems.AppIconBox
import com.cb.cb_permission.ui.ComposableItems.AppNameBox
import com.cb.cb_permission.ui.ComposableItems.Footer
import com.cb.cb_permission.ui.ComposableItems.PermissionButton
import com.cb.cb_permission.ui.ComposableItems.Space
import kotlinx.coroutines.delay

object ComposableWrapper {

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

        WelcomeScreen(
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

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun WelcomeScreen(
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
            UpperHalf(
                appIcon = appIcon,
                appName = appName,
                appNameColor = appNameColor,
                appDesc = appDesc,
                appDescColor = appDescColor
            )
            LowerHalf(
                context = context,
                visiblePermission = visiblePermission,
                currentPermission = currentPermission,
                onclickSkip = onclickSkip,
                permissionCardBackground = permissionCardBackground,
                permissionTextColor = permissionTextColor,
                skipButtonColor = skipButtonColor,
                skipButtonTextColor = skipButtonTextColor,
                backgroundColor = backgroundColor
            )
        }
    }

    @Composable
    fun UpperHalf(
        @DrawableRes appIcon: Int,
        appName: String,
        appNameColor: Color,
        appDesc: String,
        appDescColor: Color
    ) {
        Column {
            Space(6)
            AppIconBox(appIcon = appIcon)
            Space(2)
            AppNameBox(appName = appName, appNameColor = appNameColor)
            Space(2)
            AppDescBox(appDesc = appDesc, appDescColor = appDescColor)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun LowerHalf(
        context: Activity,
        visiblePermission: MutableState<Boolean>,
        currentPermission: String,
        onclickSkip: () -> Unit,
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
            PermissionButton(
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
}