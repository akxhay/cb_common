package com.cb.cbcommon.presentation.screen

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.composables.CbTextInputWithError
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    darkTheme: MutableState<Boolean>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "title",
                navController = navController,
                darkTheme = darkTheme
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            val text: MutableState<String> = remember { mutableStateOf("") }
            val error: MutableState<String?> = remember {
                mutableStateOf(
                    if (text.value.isEmpty()) {
                        "Cannot be empty"
                    } else {
                        null
                    }
                )
            }
            val onValueChange: (String) -> Unit = {
                text.value = it
                error.value = if (it.isEmpty()) {
                    "Cannot be empty"
                } else if (it.contains("x")) {
                    "Rule name already exists"
                } else {
                    null
                }
            }
            Text(text = text.value)

            CbTextInputWithError(
                label = "input",
                input = text,
                error = error,
                onValueChange = onValueChange,
                dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                horizontalPadding = 10.dp,
                onClearClick = {}
            )
            ListItem(
                headlineText = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CbTextInputWithError(
                            label = "input",
                            input = text,
                            error = error,
                            onValueChange = onValueChange,
                            dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                            onClearClick = {}

                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        CbTextInputWithError(
                            label = "input",
                            input = text,
                            error = error,
                            onValueChange = onValueChange,
                            dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                            onClearClick = {}


                        )
                    }
                },
                trailingContent = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete"
                        )
                    }
                }
            )

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navController: NavController,
    darkTheme: MutableState<Boolean>
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
            IconButton(onClick = {
                darkTheme.value = !darkTheme.value
            }) {
                Icon(
                    if (darkTheme.value) Icons.Default.LightMode else Icons.Default.DarkMode,
                    "DarkMode",
                    tint = Color.White
                )
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "Menu icon", tint = Color.White)
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        navController.navigate(Screen.ExceptionScreen.route)
                    },
                    text =
                    {
                        Text(text = "Exception")
                    }
                )
                DropdownMenuItem(
                    onClick = {
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

