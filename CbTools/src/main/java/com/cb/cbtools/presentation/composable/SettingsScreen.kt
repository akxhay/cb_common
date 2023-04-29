package com.cb.cbtools.presentation.composable

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.dynamic.data.PreferenceCategory
import com.cb.cbtools.dynamic.util.ActionResolver
import com.cb.cbtools.dynamic.util.IconResolver
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.Font.getGoogleFontFamily


@Composable
fun SettingsScreen(
    navController: NavController,
    activity: Activity,
    dynamicConfig: DynamicConfig,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    dividerColor: Color = Color.Transparent
) {
    Scaffold(
        topBar = {
            CbAppBar(
                title = "Settings",
                backAction = { navController.navigateUp() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(backgroundColor)
        ) {
            Settings(
                dynamicConfig = dynamicConfig,
                activity = activity,
                backgroundColor = backgroundColor,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                dividerColor = dividerColor
            )
        }
    }
}

@Composable
fun Settings(
    activity: Activity,
    dynamicConfig: DynamicConfig,
    backgroundColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    dividerColor: Color
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
                    activity = activity,
                    backgroundColor = backgroundColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor,
                    dividerColor = dividerColor
                )
            }
        }

    }

}

@Composable
fun PreferenceCategoryComposable(
    preferenceCategory: PreferenceCategory,
    activity: Activity,
    dynamicConfig: DynamicConfig,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    dividerColor: Color = Color.Transparent

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
        PreferenceHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            title = preferenceCategory.title!!
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
                    titleUnit = {
                        Text(
                            text = preference.title!!.replace(
                                "#APP_NAME#",
                                dynamicConfig.getAppName()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = getGoogleFontFamily(
                                name = "Poppins",
                                weights = listOf(
                                    FontWeight.Medium
                                )
                            ),
                            color = primaryTextColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    summaryUnit = {
                        preference.summary?.let { it ->
                            Text(
                                text = it.replace("#APP_NAME#", dynamicConfig.getAppName()),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = getGoogleFontFamily(
                                    name = "Roboto",
                                    weights = listOf(
                                        FontWeight.Light
                                    )
                                ),
                                color = secondaryTextColor,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }

                    },

                    primaryBitmap = if (preference.icon != null && preference.icon!!.imageVector == null) IconResolver.getBitmap(
                        icon = preference.icon!!
                    ) else null,
                    primaryImageVector = if (preference.icon != null && preference.icon!!.imageVector == null) null else IconResolver.getImageVector(
                        preference.icon,
                        Icons.Filled.Settings
                    ),
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
                    color = dividerColor
                )
            }
        }
    }

}

@Composable
fun PreferenceHeader(modifier: Modifier, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}




