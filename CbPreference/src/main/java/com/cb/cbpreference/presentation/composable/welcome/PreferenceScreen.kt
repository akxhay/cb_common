package com.cb.cbpreference.presentation.composable.welcome

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    context: Activity,
    visiblePermission: MutableState<Boolean>,
    currentPermission: String,
    appName: String,
    appDesc: String,
    onclickSkip: () -> Unit,
    appNameColor: Color,
    appDescColor: Color,
    permissionCardBackground: Color,
    permissionTextColor: Color,
    skipButtonColor: Color,
    skipButtonTextColor: Color,
    backgroundColor: Color,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                navController = navController,
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Greeting("Android")
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navController: NavController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title =
        {
            Text(title, color = MaterialTheme.colorScheme.primary)
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )

}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreferenceCategory(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Ads"
        )
        Preference(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Click here to enable Pro",
            summary = "Pro mode disabled",
            icon = Icons.Filled.MonetizationOn,
        ) {
            Toast.makeText(context, "Ad clicked", Toast.LENGTH_SHORT).show()
        }
        Spacer(modifier = Modifier.height(10.dp))
        PreferenceCategory(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Tools"
        )
        Preference(
            Modifier
                .fillMaxWidth(),
            title = "Notification access",
            summary = "Allow #APP_NAME# to access notification",
            icon = Icons.Filled.Notifications,
        ) {
            Toast.makeText(context, "Notification clicked", Toast.LENGTH_SHORT).show()
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting("Android")
}
