package com.cb.cbcommon.presentation.route

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(
        "home_screen"
    )

    data object SettingsScreen : Screen(
        "settings_screen"
    )

    data object ExceptionScreen : Screen(
        "exception_screen"
    )
}
