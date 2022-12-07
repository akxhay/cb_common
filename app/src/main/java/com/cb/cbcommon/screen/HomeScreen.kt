package com.cb.cbcommon.screen

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbcommon.route.Screen
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "title",
                navController = navController,
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "Hello world!")

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navController: NavController
) {
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title =
        {
            Text(title, color = MaterialTheme.colorScheme.onPrimary)
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "Menu icon", tint = Color.White)
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate(Screen.SettingsScreen.route)
                },
                    text =
                    {
                        Text(text = "Settings")
                    }
                )
            }
        }
    )

}


