package com.cb.cbcommon.screen

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbpreference.presentation.composable.CbPreference
import com.cb.cbpreference.util.getPreferenceScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val preferencesScreen = getPreferenceScreen(LocalContext.current)
    val map = remember { mutableStateOf(HashMap<String, Any>()) }

    preferencesScreen.observables?.let {
        for (item in it) {
            map.value[item.key] = item.value
        }
    }


    CbPreference.SettingsScreen(
        navController = navController,
        preferencesScreen = preferencesScreen,
        map = map
    )
}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





