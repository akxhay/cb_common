package com.cb.cbcommon.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbpreference.presentation.composable.CbPreference
import com.cb.cbpreference.util.getPreferenceScreen

@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val preferencesScreen = getPreferenceScreen(LocalContext.current)
    CbPreference.SettingsScreen(
        navController = navController,
        preferencesScreen = preferencesScreen,
    )
}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





