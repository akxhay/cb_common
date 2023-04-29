package com.cb.cbcommon.presentation.screen

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.constants.ActionType
import com.cb.cbtools.presentation.common.*

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
                titleUnit = {
                    Text(
                        text = "AKshay",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                summaryUnit = {
                    Text(
                        text = "sharma",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                primaryDrawable = context.getDrawable(R.drawable.play_store_512),
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
            val selectedOption = remember {
                mutableStateOf(1)
            }
            val assistantRadioOptions = listOf("Disable", "Enable", "Test")

            CbRadioGroup(
                selectedOption.value,
                assistantRadioOptions,
            ) {

            }
            ErrorInfoCard(
                message = "*Assistant is disabled"
            )
            InfoCard(
                message = "*Assistant is enabled",
            )

        }
    }

}


@Composable
fun TopAppBar(
    title: String,
    navController: NavController,
    darkTheme: MutableState<Boolean>
) {
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    CbAppBar(
        title = title,
        drawable = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_bot
        ),
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
        },
    )


}


