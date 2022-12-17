package com.cb.cbcommon.screen

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.presentation.composable.CbPreference

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    observableMap: MutableState<HashMap<String, Any>>,
    preferencesScreen: PreferenceScreen
) {

    Column(modifier = Modifier.fillMaxSize()) {
        CbPreference.SettingsScreen(
            navController = navController,
            preferencesScreen = preferencesScreen,
            map = observableMap
        )
    }


}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





