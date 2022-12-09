package com.cb.cbcommon.screen

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbcommon.ui.theme.Blue40
import com.cb.cbcommon.ui.theme.lightAppBar
import com.cb.cbpreference.presentation.composable.CbPreference
import com.cb.cbpreference.util.getPreferenceScreen
import java.util.*

@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    context: Activity,
) {
    val preferencesScreen = getPreferenceScreen(LocalContext.current)
    CbPreference.SettingsScreen(
        navController = navController,
        preferencesScreen = preferencesScreen,
        headerColor = lightAppBar,
        iconColor = Blue40
    )
}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





