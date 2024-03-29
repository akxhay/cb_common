@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon.presentation

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.page.AddAppScreen
import com.cb.cbcommon.presentation.page.CardsScreen
import com.cb.cbcommon.presentation.page.HomeScreen
import com.cb.cbcommon.presentation.page.InputScreen
import com.cb.cbcommon.presentation.page.ListItemsScreen
import com.cb.cbcommon.presentation.page.TabScreen
import com.cb.cbcommon.presentation.page.TestChatBinScreen
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.presentation.composable.ExceptionScreen
import com.cb.cbtools.presentation.composable.SettingsScreen

@Composable
fun CbApp(
    navController: NavHostController,
    activity: Activity,
    theme: Int,
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
                theme,
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
        composable(
            route = Screen.AddAppScreen.route
        ) {
            OpenAppScreen(navController)
        }
        composable(
            route = Screen.TabScreen.route
        ) {
            OpenTabScreen(navController)
        }

        composable(
            route = Screen.TestChatBinScreen.route
        ) {
            OpenTestChatBinScreen(navController)
        }
    }

}


@Composable
fun OpenHomeScreen(
    navController: NavHostController,
    theme: Int,
    toggleDarkMode: () -> Unit,
) {
    HomeScreen(
        navController = navController,
        theme = theme,
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
    ExceptionScreen(navController = navController)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OpenInputScreen(
    navController: NavHostController,
) {
    InputScreen(navController = navController)
}

@Composable
fun OpenListItemsScreen(
    navController: NavHostController,
) {
    ListItemsScreen(navController = navController)
}

@Composable
fun OpenCardsScreen(
    navController: NavHostController,
) {
    CardsScreen(navController = navController)
}

@Composable
fun OpenAppScreen(navController: NavHostController) {
    AddAppScreen(navController = navController)
}

@Composable
fun OpenTabScreen(navController: NavHostController) {
    TabScreen(navController = navController)
}

@Composable
fun OpenTestChatBinScreen(navController: NavHostController) {
    TestChatBinScreen(navController = navController)
}


