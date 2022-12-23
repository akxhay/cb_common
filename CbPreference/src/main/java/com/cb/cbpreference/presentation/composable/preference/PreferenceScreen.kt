package com.cb.cbpreference.presentation.composable.preference

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbpreference.data.PreferenceCategory
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.data.PreferenceStyle
import com.cb.cbpreference.util.ActionResolver
import com.cb.cbpreference.util.ColorResolver
import com.cb.cbpreference.util.IconResolver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreenComposable(
    navController: NavController,
    preferencesScreen: PreferenceScreen,
    activity: Activity
) {
    var style = preferencesScreen.style
    if (style == null) style = PreferenceStyle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                titleColor = ColorResolver.getColor(
                    color = style.appbarTitleColor,
                    MaterialTheme.colorScheme.onPrimary
                ),
                backgroundColor = ColorResolver.getColor(
                    color = style.appbarBackgroundColor, MaterialTheme.colorScheme.primary
                ),
                navController = navController,
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Settings(
                titleColor = ColorResolver.getColor(
                    color = style.titleColor,
                    MaterialTheme.colorScheme.onSurface
                ),
                headerColor = ColorResolver.getColor(
                    color = style.headerColor,
                    MaterialTheme.colorScheme.onSurface
                ),
                summaryColor = ColorResolver.getColor(
                    color = style.summaryColor,
                    MaterialTheme.colorScheme.onSurfaceVariant
                ),
                dividerColor = ColorResolver.getColor(
                    color = style.dividerColor, MaterialTheme.colorScheme.primary
                ),
                iconColor = ColorResolver.getColor(
                    color = style.iconColor,
                    MaterialTheme.colorScheme.primary
                ),
                backgroundColor = ColorResolver.getColor(
                    color = style.backgroundColor, MaterialTheme.colorScheme.surface
                ),
                preferencesScreen = preferencesScreen,
                activity = activity
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
    titleColor: Color,
    headerColor: Color,
    summaryColor: Color,
    dividerColor: Color,
    iconColor: Color,
    backgroundColor: Color,
    preferencesScreen: PreferenceScreen,
    activity: Activity
) {
    val preferenceCategories: List<PreferenceCategory>? = preferencesScreen.preferences
    preferenceCategories?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
                    preferencesScreen = preferencesScreen,
                    activity = activity

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
    preferencesScreen: PreferenceScreen,
    activity: Activity
) {
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
                    preference = preference,
                    modifier = Modifier.fillMaxWidth(),
                    titleColor = titleColor,
                    icon = IconResolver.getImageVector(
                        preference.icon,
                        Icons.Filled.Settings
                    ),
                    summaryColor = summaryColor,
                    iconColor = iconColor,
                    preferenceType = preference.type,
                    preferencesScreen = preferencesScreen
                ) {
                    ActionResolver.getAction(
                        preferencesScreen.appName,
                        activity,
                        preference.action
                    )()
                }
                Divider(
                    color = dividerColor
                )
            }
        }
    }
}
