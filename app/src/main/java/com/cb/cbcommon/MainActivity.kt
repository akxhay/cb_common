@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.route.Screen
import com.cb.cbcommon.screen.HomeScreen
import com.cb.cbcommon.screen.SettingsScreen
import com.cb.cbcommon.ui.theme.CbCommonTheme
import com.cb.cbtools.ccp.component.CbCCC
import com.cb.cbtools.ccp.component.getFullPhoneNumber
import com.cb.cbtools.ccp.component.isPhoneNumber
import com.cb.cbtools.composables.CbGenericDialog


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = remember {
                mutableStateOf(false)
            }
            CbCommonTheme(darkTheme = darkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            OpenHomeScreen(navController, darkTheme)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            OpenSettingsScreen(navController)
                        }
                    }

                }
            }
        }
    }


    @Composable
    fun ShowDialog(
        showAlert: MutableState<Boolean>,
    ) {
        val context = LocalContext.current
        val phoneNumber = rememberSaveable { mutableStateOf("") }
        CbGenericDialog(
            showAlert = showAlert,
            onPositiveClick = {
                if (!isPhoneNumber()) {
                    Toast.makeText(context, getFullPhoneNumber(), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter valid number", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            title = "Send message without saving number",
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CbCCC(
                        text = phoneNumber.value,
                        onValueChange = { phoneNumber.value = it },
                    )

                }
            },
            dynamicConfig = BaseApplication.getInstance().dynamicConfig
        )
    }


    @Composable
    fun FloatingActionButtons(
        backgroundColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        icon: ImageVector = Icons.Default.Add,
        onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {

            FloatingActionButton(
                onClick = onClick,
                containerColor = backgroundColor,
                contentColor = contentColor
            ) {
                Icon(icon, "icon")
            }

        }
    }


    @Composable
    fun OpenHomeScreen(
        navController: NavHostController,
        darkTheme: MutableState<Boolean>
    ) {

        HomeScreen(navController = navController, darkTheme)
        val dialog = remember { mutableStateOf(false) }

        FloatingActionButtons(icon = Icons.Default.Chat) {
            dialog.value = true
        }
        if (dialog.value) {
            ShowDialog(
                showAlert = dialog
            )
        }
    }

    @Composable
    fun OpenSettingsScreen(
        navController: NavHostController,
    ) {
        SettingsScreen(navController = navController, activity = this)
    }

}