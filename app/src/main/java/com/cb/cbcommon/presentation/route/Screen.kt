package com.cb.cbcommon.presentation.route

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(
        "home_screen"
    )

    data object InputScreen : Screen(
        "input_screen"
    )

    data object ListItemsScreen : Screen(
        "list_items_screen"
    )

    data object CardsScreen : Screen(
        "cards_screen"
    )

    data object SettingsScreen : Screen(
        "settings_screen"
    )

    data object ExceptionScreen : Screen(
        "exception_screen"
    )

    data object AddAppScreen : Screen(
        "add_app_screen"
    )
    data object TabScreen : Screen(
        "tab_screen"
    )
}
