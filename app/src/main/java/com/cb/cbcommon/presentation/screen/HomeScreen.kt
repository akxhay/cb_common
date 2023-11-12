package com.cb.cbcommon.presentation.screen

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.ccp.component.CbCCC
import com.cb.cbtools.ccp.data.utils.checkPhoneNumber
import com.cb.cbtools.ccp.data.utils.getDefaultLangCode
import com.cb.cbtools.ccp.data.utils.getDefaultPhoneCode
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbFab
import com.cb.cbtools.presentation.common.CbGenericDialog
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorPrimary
import com.cb.cbtools.presentation.common.CbListItemTitle

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
) {
    val dialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = "title",
                navController = navController,
                darkMode = darkMode,
                toggleDarkMode = toggleDarkMode
            )
        },
        floatingActionButton = {
            CbFab(icon = Icons.Default.ChatBubble, onClick = {
                dialog.value = true
            })
        }
    ) { padding ->
        HomeScreenContent(padding = padding, navController = navController)
        if (dialog.value) {
            ShowDialog(
                dismissDialog = {
                    dialog.value = false
                }
            )
        }
    }
}

@Composable
fun HomeScreenContent(padding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
        )
        {
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.Search) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "CB input") },
                onClick = {
                    navController.navigate(Screen.InputScreen.route)
                }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.FormatListNumbered) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "CB List items") },
                onClick = {
                    navController.navigate(Screen.ListItemsScreen.route)
                }
            )

            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.CreditCard) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "CB Cards") },
                onClick = {
                    navController.navigate(Screen.CardsScreen.route)
                }
            )
        }

    }
}

@Composable
fun ShowDialog(
    dismissDialog: () -> Unit,
) {
    val context = LocalContext.current
    val phoneNumber = rememberSaveable { mutableStateOf("") }

    val phoneCode = rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context
            )
        )
    }
    val defaultLang = rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }
    val validationMessage = rememberSaveable {
        mutableStateOf("")
    }
    val onInputChanged: (String) -> Unit = {
        phoneNumber.value = it
    }
    val validation: (String) -> Boolean = {
        if (checkPhoneNumber(
                phone = phoneNumber.value,
                fullPhoneNumber = phoneCode.value + phoneNumber.value,
                countryCode = defaultLang.value
            )
        ) {
            validationMessage.value = "Please enter valid number"
            false
        } else {
            true
        }
    }
    val onClearClick: () -> Unit = {
        onInputChanged("")
    }

    CbGenericDialog(
        onConfirmClick = {
            if (validation(phoneNumber.value)) {
                Toast.makeText(
                    context,
                    phoneCode.value + phoneNumber.value,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, validationMessage.value, Toast.LENGTH_SHORT).show()
            }
        },
        onDismissClick = {
            dismissDialog()
        },
        title = "Send message without saving number",
        text = {
            CbCCC(
                phoneNumber = phoneNumber.value,
                defaultLang = defaultLang.value,
                onInputChanged = onInputChanged,
                onClearClick = onClearClick,
                validation = validation,
                validationMessage = validationMessage.value,
                onPickedCountryChange = {
                    phoneCode.value = it.countryPhoneCode
                    defaultLang.value = it.countryCode
                }
            )

        },
        confirmText = stringResource(R.string.proceed),
    )
}


@Composable
fun TopAppBar(
    title: String,
    navController: NavController,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
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
                toggleDarkMode()
            }) {
                Icon(
                    if (darkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
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


