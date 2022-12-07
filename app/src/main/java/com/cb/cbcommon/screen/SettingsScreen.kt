package com.cb.cbcommon.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import com.cb.cbpreference.presentation.composable.CbPreference
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    context: Activity,
) {

    CbPreference.SettingsScreen(
        context = context,
        currentPermission = "",
        appName = "",
        appDesc = "",
        onclickSkip = {

        },
        navController=navController
    )

}



