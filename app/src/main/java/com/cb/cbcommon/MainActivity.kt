@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.route.Screen
import com.cb.cbcommon.screen.HomeScreen
import com.cb.cbcommon.screen.SettingsScreen
import com.cb.cbcommon.ui.theme.CbCommonTheme
import com.cb.cbcpp.presentation.component.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dialog = remember { mutableStateOf(false) }
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            OpenHomeScreen(navController)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            OpenSettingsScreen(navController)
                        }
                    }
                    FloatingActionButtons(icon = Icons.Default.Chat) {
                        dialog.value = true
                    }
                    if (dialog.value) {
                        ShowDialog(
                            showAlert = dialog
                        )
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
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            confirmButton = {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                )
                {
                    TextButton(onClick = {
                        if (!isPhoneNumber()) {
                            Toast.makeText(context, getFullPhoneNumber(), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter valid number", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                    { Text(text = "Proceed", color = MaterialTheme.colorScheme.onPrimary) }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAlert.value = false
                })
                { Text(text = "Cancel") }
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Send message without saving number",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

            },
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
            }
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
        navController: NavHostController
    ) {
        HomeScreen(navController = navController)
    }

    @Composable
    fun OpenSettingsScreen(
        navController: NavHostController,
    ) {
        SettingsScreen(navController = navController, activity = this)
    }

}