package com.cb.cbtools.preference.preference

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cb.cbtools.composables.CbListItem
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.dynamic.data.PreferenceCategory
import com.cb.cbtools.dynamic.util.ActionResolver
import com.cb.cbtools.dynamic.util.IconResolver


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
                    title = preference.title!!.replace("#APP_NAME#", dynamicConfig.getAppName()),
                    summary = preference.summary?.replace("#APP_NAME#", dynamicConfig.getAppName()),
                    maxSummaryLines = Int.MAX_VALUE,
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