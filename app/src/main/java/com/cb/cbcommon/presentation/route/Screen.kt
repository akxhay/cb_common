package com.cb.cbcommon.presentation.route

sealed class Screen(val route: String) {
    object HomeScreen : Screen(
        "home_screen"
    )

    object SettingsScreen : Screen(
        "settings_screen"
    )

    object ExceptionScreen : Screen(
        "exception_screen"
    )
}
