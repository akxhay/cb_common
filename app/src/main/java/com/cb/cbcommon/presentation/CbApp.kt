package com.cb.cbcommon.presentation

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.page.CardsScreen
import com.cb.cbcommon.presentation.page.HomeScreen
import com.cb.cbcommon.presentation.page.InputScreen
import com.cb.cbcommon.presentation.page.ListItemsScreen
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.presentation.composable.ExceptionScreen
import com.cb.cbtools.presentation.composable.SettingsScreen

@Composable
fun CbApp(
    navController: NavHostController,
    activity: Activity,
    darkTheme: Boolean,
    toggleDarkMode: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            OpenHomeScreen(
                navController,
                darkTheme,
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
            OpenSettingsScreen(navController, activity)
        }
        composable(
            route = Screen.ExceptionScreen.route
        ) {
            OpenExceptionScreen(
                navController
            )
        }
        composable(
            route = Screen.CardsScreen.route
        ) {
            OpenCardsScreen(navController)
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
    activity: Activity,
) {
    val navigateUp = {
        navController.navigateUp()
    }
    SettingsScreen(
        navigateUp = navigateUp,
        activity = activity,
        dynamicConfig = BaseApplication.getInstance().dynamicConfig
    )
}

@Composable
fun OpenExceptionScreen(
    navController: NavHostController,
) {
    ExceptionScreen(
        navController = navController,
    )
}

@OptIn(ExperimentalAnimationApi::class)
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


