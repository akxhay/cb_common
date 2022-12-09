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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.route.Screen
import com.cb.cbcommon.screen.HomeScreen
import com.cb.cbcommon.screen.SettingsScreen
import com.cb.cbcommon.ui.theme.CbCommonTheme

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
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SettingsScreen.route
                    ) {
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            OpenHomeScreen(navController)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            OpenSettingsScreen(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun OpenHomeScreen(navController: NavHostController) {
        HomeScreen(navController = navController)
    }

    @Composable
    fun OpenSettingsScreen(navController: NavHostController) {
        SettingsScreen(navController = navController, context=this)
    }

}