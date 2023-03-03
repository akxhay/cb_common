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
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.constants.ActionType
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbTextDropDown
import com.cb.cbtools.presentation.common.CbTextInputWithError
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    darkTheme: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val checkedMap: MutableState<Map<String, MutableState<Boolean>>> =
        remember {
            mutableStateOf(HashMap<String, MutableState<Boolean>>().also {
                it["org.telegram.messenger.web"] = mutableStateOf(false)

            })
        }
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
            CbListItem(
                title = "Whatsapp",
                primaryDrawable = context.getDrawable(R.drawable.play_store_512),
                dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                actionType = ActionType.CHECKBOX,
                checked = checkedMap.value["org.telegram.messenger.web"],
                onChange = { value ->
                    checkedMap.value["org.telegram.messenger.web"]?.value = value
                },
                onClick = {
                    checkedMap.value["org.telegram.messenger.web"]?.value =
                        checkedMap.value["org.telegram.messenger.web"]?.value?.not() ?: false

                },
                enabled = false
            )

            CbTextDropDown(
                label = "Type",
                dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                options = arrayOf(
                    "okay", "ji",
                ),
                selectedOption = "okay"
            ) {

            }

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


