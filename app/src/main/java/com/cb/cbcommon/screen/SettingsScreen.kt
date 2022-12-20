package com.cb.cbcommon.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbpreference.presentation.composable.CbPreference
import com.cb.cbpreference.util.getPreferenceScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    activity: Activity
) {

    val observableMap = remember { mutableStateOf(HashMap<String, Any>()) }
    val preferencesScreen = getPreferenceScreen(LocalContext.current)

    preferencesScreen.observables?.let {
        for (item in it) {
            observableMap.value[item.key] = item.value
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CbPreference.SettingsScreen(
            navController = navController,
            preferencesScreen = preferencesScreen,
            map = observableMap,
            activity = activity
        )
    }


}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





