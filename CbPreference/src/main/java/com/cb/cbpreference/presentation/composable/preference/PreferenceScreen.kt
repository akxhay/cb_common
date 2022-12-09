package com.cb.cbpreference.presentation.composable.preference

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceCategory
import com.cb.cbpreference.data.PreferenceScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    appName: String,
    titleColor: Color,
    headerColor: Color,
    summaryColor: Color,
    dividerColor: Color,
    iconColor: Color,
    backgroundColor: Color,
    appbarTitleColor: Color,
    appbarBackgroundColor: Color,
    navController: NavController,
    preferencesScreen: PreferenceScreen
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                titleColor = appbarTitleColor,
                backgroundColor = appbarBackgroundColor,
                navController = navController,
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Settings(
                appName = appName,
                titleColor = titleColor,
                headerColor = headerColor,
                summaryColor = summaryColor,
                dividerColor = dividerColor,
                iconColor = iconColor,
                backgroundColor = backgroundColor,
                preferencesScreen = preferencesScreen
            )
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    titleColor: Color,
    backgroundColor: Color,
    navController: NavController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
        title =
        {
            Text(title, color = titleColor)
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = titleColor
                )
            }
        }
    )

}

@Composable
fun Settings(
    appName: String = "Application",
    titleColor: Color,
    headerColor: Color,
    summaryColor: Color,
    dividerColor: Color,
    iconColor: Color,
    backgroundColor: Color,
    preferencesScreen: PreferenceScreen
) {
    val context = LocalContext.current
    val preferenceCategories: List<PreferenceCategory>? = preferencesScreen.preferences
    preferenceCategories?.let {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            items(it) { preferenceCategory ->
                PreferenceCategoryComposable(
                    preferenceCategory = preferenceCategory,
                    titleColor = titleColor,
                    headerColor = headerColor,
                    summaryColor = summaryColor,
                    dividerColor = dividerColor,
                    iconColor = iconColor,
                    backgroundColor = backgroundColor,
                    preferencesScreen = preferencesScreen
                )
            }
        }

    }

}

@Composable
fun PreferenceCategoryComposable(
    preferenceCategory: PreferenceCategory,
    titleColor: Color,
    headerColor: Color,
    summaryColor: Color,
    dividerColor: Color,
    iconColor: Color,
    backgroundColor: Color,
    preferencesScreen: PreferenceScreen
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        PreferenceHeader(
            modifier = Modifier
                .fillMaxWidth(),
            title = preferenceCategory.title!!,
            color = headerColor
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        preferenceCategory.preferences?.let {
            for (preference in it) {
                PreferenceComposable(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = preference.title!!,
                    titleColor = titleColor,
                    icon = Icons.Filled.MonetizationOn,
                    summary = preference.summary!!,
                    summaryColor = summaryColor,
                    iconColor = iconColor,
                ) {
                    Toast.makeText(context, "Ad clicked", Toast.LENGTH_SHORT).show()
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
            }
        }
    }
}
