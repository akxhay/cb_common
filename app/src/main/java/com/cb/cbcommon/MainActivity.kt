@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.route.Screen
import com.cb.cbcommon.screen.HomeScreen
import com.cb.cbcommon.screen.SettingsScreen
import com.cb.cbcommon.ui.theme.CbCommonTheme
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.util.getPreferenceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val observableMap = remember { mutableStateOf(HashMap<String, Any>()) }
                    val preferencesScreen = getPreferenceScreen(LocalContext.current)

                    preferencesScreen.observables?.let {
                        for (item in it) {
                            observableMap.value[item.key] = item.value
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            OpenHomeScreen(navController, observableMap)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            OpenSettingsScreen(navController, observableMap, preferencesScreen)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun OpenHomeScreen(
        navController: NavHostController,
        observableMap: MutableState<HashMap<String, Any>>
    ) {
        HomeScreen(navController = navController, observableMap)
    }

    @Composable
    fun OpenSettingsScreen(
        navController: NavHostController,
        observableMap: MutableState<HashMap<String, Any>>,
        preferencesScreen: PreferenceScreen
    ) {
        SettingsScreen(navController = navController, observableMap,preferencesScreen)
    }

}