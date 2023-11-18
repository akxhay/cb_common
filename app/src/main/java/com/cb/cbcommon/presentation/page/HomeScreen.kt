package com.cb.cbcommon.presentation.page

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
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
import androidx.compose.material.icons.filled.Tab
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.route.Screen
import com.cb.cbtools.ccp.component.ShowCCDialog
import com.cb.cbtools.presentation.common.AppIcon
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbFab
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorPrimary
import com.cb.cbtools.presentation.common.CbListItemTitle

@Composable
fun HomeScreen(
    navController: NavController,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
) {
    val dialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Home",
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
            ShowCCDialog(
                dismissDialog = {
                    dialog.value = false
                },
                onProceed = { code, number ->
                    Toast.makeText(context, "code: $code, number: $number", Toast.LENGTH_SHORT)
                        .show()
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
            CbListItem(
                iconUnit = {
                    AppIcon(
                        BaseApplication.getInstance().packageName,
                        BaseApplication.getInstance().appInfoService
                    )
                },
                titleUnit = { CbListItemTitle(text = "App search") },
                onClick = {
                    navController.navigate(Screen.AddAppScreen.route)
                }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.Tab) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "Tab layout") },
                onClick = {
                    navController.navigate(Screen.TabScreen.route)
                }
            )
        }

    }
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
                        showMenu = false
                        navController.navigate(Screen.ExceptionScreen.route)
                    },
                    text =
                    {
                        Text(text = "Exception")
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
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


