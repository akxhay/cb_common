package com.cb.cbcommon.presentation.route

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(
        "home_screen"
    )

    data object SearchBarScreen : Screen(
        "search_bar_screen"
    )

    data object ListItemsScreen : Screen(
        "list_items_screen"
    )

    data object SettingsScreen : Screen(
        "settings_screen"
    )

    data object ExceptionScreen : Screen(
        "exception_screen"
    )
}
