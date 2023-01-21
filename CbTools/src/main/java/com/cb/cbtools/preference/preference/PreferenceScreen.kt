package com.cb.cbtools.preference.preference

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
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.composables.CbListItem
import com.cb.cbtools.dynamic.data.PreferenceCategory
import com.cb.cbtools.dynamic.util.ActionResolver
import com.cb.cbtools.dynamic.util.IconResolver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreenComposable(
    navController: NavController,
    activity: Activity,
    dynamicConfig: DynamicConfig
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                titleColor = dynamicConfig.getAppBarTitleColor(),
                backgroundColor = dynamicConfig.getAppBarBackGroundColor(),
                navController = navController,
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Settings(
                dynamicConfig = dynamicConfig,
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
    activity: Activity,
    dynamicConfig: DynamicConfig
) {
    dynamicConfig.getPreferenceCategories()?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(it) { preferenceCategory ->
                PreferenceCategoryComposable(
                    preferenceCategory = preferenceCategory,
                    dynamicConfig = dynamicConfig,
                    activity = activity
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceCategoryComposable(
    preferenceCategory: PreferenceCategory,
    activity: Activity,
    dynamicConfig: DynamicConfig
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = dynamicConfig.getBackgroundColor()
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(5.dp)
        )
        PreferenceHeader(
            modifier = Modifier
                .fillMaxWidth(),
            title = preferenceCategory.title!!,
            color = dynamicConfig.getPrimaryTextOnBackGroundColor()
        )
        Spacer(
            modifier = Modifier
                .height(5.dp)
        )
        preferenceCategory.preferences?.let {
            for (preference in it) {
                val isChecked = remember {
                    mutableStateOf(
                        dynamicConfig.getSharedPreferences()
                            .getBoolean(preference.pref, true)
                    )
                }
                CbListItem(
                    title = preference.title!!.replace("#APP_NAME#", dynamicConfig.getAppName()),
                    summary = preference.summary?.replace("#APP_NAME#", dynamicConfig.getAppName()),
                    primaryBitmap = if (preference.icon != null && preference.icon!!.imageVector == null) IconResolver.getBitmap(
                        icon = preference.icon!!
                    ) else null,
                    primaryImageVector = if (preference.icon != null && preference.icon!!.imageVector == null) null else IconResolver.getImageVector(
                        preference.icon,
                        Icons.Filled.Settings
                    ),
                    dynamicConfig = dynamicConfig,
                    actionType = preference.type,
                    checked = isChecked,
                    onChange = { value ->
                        dynamicConfig.getSharedPreferences().edit()
                            .putBoolean(preference.pref, value).apply()
                    },
                    onClick = {
                        ActionResolver.getAction(
                            dynamicConfig.getAppName(),
                            activity,
                            preference.action
                        )()
                    }
                )

                Divider(
                    color = dynamicConfig.getDividerColor()
                )
            }
        }
    }

}
