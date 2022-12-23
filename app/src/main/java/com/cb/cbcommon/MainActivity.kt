@file:OptIn(ExperimentalAnimationApi::class)

package com.cb.cbcommon

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.text.TextStyle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme {
                // A surface container using the 'background' color from the theme
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
                TextButton(onClick = {
                    if (!isPhoneNumber()) {
                        CoroutineScope(Dispatchers.Default).launch {
                            sendWhatsappBlank(context, getFullPhoneNumber())
                        }
                    } else {
                        Toast.makeText(context, "Please enter valid number", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                { Text(text = "Proceed") }
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
                        text = "Send text without saving number",
                        style = TextStyle(color = Color.Black, fontSize = 18.sp)
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

    private fun sendWhatsappBlank(context: Context, phoneStr: String) {
        val packageManager = context.packageManager
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url = "https://api.whatsapp.com/send?phone=$phoneStr"
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i)
            }
            context.startActivity(i)
        } catch (e: Exception) {

        }
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