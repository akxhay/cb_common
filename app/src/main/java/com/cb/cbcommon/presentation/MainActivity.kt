@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbcommon.presentation.screen.CardsScreen
import com.cb.cbcommon.presentation.screen.HomeScreen
import com.cb.cbcommon.presentation.screen.InputScreen
import com.cb.cbcommon.presentation.screen.ListItemsScreen
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.presentation.common.RatePopUp
import com.cb.cbtools.presentation.composable.ExceptionScreen
import com.cb.cbtools.presentation.composable.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var showRatePopUp: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = remember {
                mutableStateOf(false)
            }
            val toggleDarkMode: () -> Unit = {
                darkTheme.value = !darkTheme.value
            }
            showRatePopUp = remember {
                mutableStateOf(false)
            }
            CbCommonTheme(darkTheme = darkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            OpenHomeScreen(
                                navController,
                                darkTheme.value,
                                toggleDarkMode
                            )
                        }
                        composable(
                            route = Screen.InputScreen.route
                        ) {
                            OpenInputScreen(navController)
                        }
                        composable(
                            route = Screen.ListItemsScreen.route
                        ) {
                            OpenListItemsScreen(navController)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            OpenSettingsScreen(navController)
                        }
                        composable(
                            route = Screen.ExceptionScreen.route
                        ) {
                            OpenExceptionScreen(navController)
                        }
                        composable(
                            route = Screen.CardsScreen.route
                        ) {
                            OpenCardsScreen(navController)
                        }
                    }
                    if (showRatePopUp.value)
                        RatePopUp(
                            dismiss = { showRatePopUp.value = false },
                            activity = this,
                        )
                }
            }
        }
    }


    @Composable
    fun OpenHomeScreen(
        navController: NavHostController,
        darkMode: Boolean,
        toggleDarkMode: () -> Unit,
    ) {
        HomeScreen(
            navController = navController,
            darkMode = darkMode,
            toggleDarkMode = toggleDarkMode
        )
    }

    @Composable
    fun OpenSettingsScreen(
        navController: NavHostController,
    ) {
        SettingsScreen(
            navController = navController,
            activity = this,
            dynamicConfig = BaseApplication.getInstance().dynamicConfig
        )
    }

    @Composable
    fun OpenExceptionScreen(
        navController: NavHostController,
    ) {
        ExceptionScreen(
            navController = navController
        )
    }

    @Composable
    fun OpenInputScreen(
        navController: NavHostController,
    ) {
        InputScreen(
            navController = navController
        )
    }

    @Composable
    fun OpenListItemsScreen(
        navController: NavHostController,
    ) {
        ListItemsScreen(
            navController = navController
        )
    }

    @Composable
    fun OpenCardsScreen(
        navController: NavHostController,
    ) {
        CardsScreen(
            navController = navController
        )
    }

    override fun onResume() {
        super.onResume()
        if (this::showRatePopUp.isInitialized) {
            showRatePopUp.value = true
        }
    }


}