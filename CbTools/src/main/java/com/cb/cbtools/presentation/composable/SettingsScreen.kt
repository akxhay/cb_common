package com.cb.cbtools.presentation.composable

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.dynamic.data.PreferenceCategory
import com.cb.cbtools.dynamic.util.ActionResolver
import com.cb.cbtools.dynamic.util.ExpressionResolver
import com.cb.cbtools.dynamic.util.IconResolver
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemAction
import com.cb.cbtools.presentation.common.CbListItemIconDouble
import com.cb.cbtools.presentation.common.CbListItemIconImageBitmapPrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorSecondary
import com.cb.cbtools.presentation.common.CbListItemSummary
import com.cb.cbtools.presentation.common.CbListItemTitle


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
                .background(MaterialTheme.colorScheme.primary)
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
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
    )
    {
        var counter by remember { mutableStateOf(GlobalState(0)) }

        dynamicConfig.getPreferenceCategories()?.let {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                items(it) { preferenceCategory ->
                    if (ExpressionResolver.evaluate(
                            dynamicConfig.getSharedPreferences(),
                            preferenceCategory.showExpression
                        )
                    ) {
                        PreferenceCategoryComposable(
                            preferenceCategory = preferenceCategory,
                            dynamicConfig = dynamicConfig,
                            activity = activity,
                            backgroundColor = backgroundColor,
                            primaryTextColor = primaryTextColor,
                            secondaryTextColor = secondaryTextColor,
                            dividerColor = dividerColor,
                            globalState = counter,
                            onUpdateCount = {
                                counter = counter.copy(count = counter.count + 1)
                            }
                        )
                    }
                }
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
    dividerColor: Color = Color.Transparent,
    globalState: GlobalState,
    onUpdateCount: () -> Unit

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
                Log.i("globalState", globalState.count.toString())
                if (ExpressionResolver.evaluate(
                        dynamicConfig.getSharedPreferences(),
                        preference.showExpression
                    )
                ) {
                    val isChecked = remember {
                        mutableStateOf(
                            dynamicConfig.getSharedPreferences()
                                .getBoolean(preference.pref, true)
                        )
                    }
                    if (globalState.count < 0)
                        Text("count ${globalState.count}")
                    CbListItem(
                        iconUnit = {
                            if (preference.icon != null && preference.icon!!.imageVector == null) {
                                CbListItemIconDouble(primaryIconUnit = {
                                    CbListItemIconImageBitmapPrimary(
                                        bitmap = IconResolver.getBitmap(
                                            icon = preference.icon!!
                                        )
                                    ) {

                                    }
                                }, secondaryIconUnit = {
                                    CbListItemIconImageVectorSecondary(
                                        imageVector = IconResolver.getImageVector(
                                            preference.icon,
                                            Icons.Filled.Settings
                                        )
                                    )
                                }
                                )
                            }
                        },
                        titleUnit = {
                            CbListItemTitle(
                                text = preference.title!!.replace(
                                    "#APP_NAME#",
                                    dynamicConfig.getAppName()
                                )
                            )
                        },
                        summaryUnit = {
                            preference.summary?.let { it ->
                                CbListItemSummary(
                                    text = it.replace(
                                        "#APP_NAME#",
                                        dynamicConfig.getAppName()
                                    )
                                )
                            }

                        },
                        actionUnit = {
                            CbListItemAction(
                                actionType = preference.type,
                                state = isChecked.value
                            ) {
                                isChecked.value = !isChecked.value
                                dynamicConfig.getSharedPreferences().edit()
                                    .putBoolean(preference.pref, isChecked.value).apply()
                            }
                        },
                        onClick = {
                            if (preference.showExpression != null)
                                onUpdateCount()
                            ActionResolver.getAction(
                                dynamicConfig.getAppName(),
                                activity,
                                preference.action
                            )()
                        }
                    )
                    HorizontalDivider(
                        color = dividerColor
                    )
                }
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
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

data class GlobalState(var count: Int = 0)



