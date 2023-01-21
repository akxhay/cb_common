package com.cb.cbcommon.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbtools.preference.CbPreference

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    navController: NavController,
    activity: Activity
) {

    Column(modifier = Modifier.fillMaxSize()) {
        CbPreference.SettingsScreen(
            navController = navController,
            dynamicConfig = BaseApplication.getInstance().dynamicConfig,
            activity = activity
        )
    }


}


//here Gson() is basically providing fromJson methods which actually //deserializing json. It basically parse json string to //list<Country> object
//this getCountryCode(ctx: Context) will return a list of Country data class.





