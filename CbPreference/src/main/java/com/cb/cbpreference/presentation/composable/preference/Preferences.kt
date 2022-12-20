package com.cb.cbpreference.presentation.composable.preference

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cbpreference.constants.PreferenceType
import com.cb.cbpreference.data.Preference
import com.cb.cbpreference.data.PreferenceIcon
import com.cb.cbpreference.data.PreferenceScreen
import com.cb.cbpreference.util.IconResolver


@Composable
fun PreferenceCategory(modifier: Modifier, title: String, color: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            PreferenceCategoryTitle(title, color)
        }
    }

}

@Composable
fun PreferenceHeader(modifier: Modifier, title: String, color: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            PreferenceCategoryTitle(title, color)
        }
    }

}

@Composable
fun PreferenceComposable(
    preference: Preference,
    modifier: Modifier,
    icon: ImageVector,
    titleColor: Color,
    summaryColor: Color,
    iconColor: Color,
    preferenceType: PreferenceType = PreferenceType.DEFAULT,
    preferencesScreen: PreferenceScreen,
    onclick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (preference.icon != null) {
                PreferenceIcon(preference.icon!!)
            } else {
                PreferenceIcon(icon, iconColor)
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(3.0f, true)) {
                PreferenceTitle(
                    preference.title!!.replace("#APP_NAME#", preferencesScreen.appName),
                    titleColor
                )
                preference.summary?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    PreferenceSummary(
                        it.replace("#APP_NAME#", preferencesScreen.appName),
                        summaryColor
                    )
                }
            }
            if (preferenceType != PreferenceType.DEFAULT) {
                Column(
                    modifier = Modifier.weight(1.0f, true),
                    horizontalAlignment = Alignment.End
                ) {
                    PreferenceAction(
                        preferenceType,
                        preference.pref,
                        preferencesScreen.sharedPrefName
                    )

                }
            }
        }
    }

}

@Composable
fun PreferenceAction(
    preferenceType: PreferenceType,
    observe: String?,
    sharedPrefName: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(end = 20.dp)
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        if (preferenceType == PreferenceType.SWITCH) {
            val checkedState = remember {
                mutableStateOf(
                    context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                        .getBoolean(observe, true)
                )
            }
            Switch(
                modifier = Modifier.padding(end = 20.dp),
                checked = checkedState.value,
                onCheckedChange = { changedValue ->
                    checkedState.value = changedValue
                    observe?.let {
                        context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).edit()
                            .putBoolean(observe, changedValue).apply()
                    }
                }
            )
        } else if (preferenceType == PreferenceType.CHECKBOX) {
            val checkedState = remember {
                mutableStateOf(
                    context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                        .getBoolean(observe, true)
                )
            }
            Checkbox(
                modifier = Modifier.padding(end = 20.dp),
                checked = checkedState.value,
                onCheckedChange = { changedValue ->
                    checkedState.value = changedValue
                    observe?.let {
                        context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).edit()
                            .putBoolean(observe, changedValue).apply()
                    }
                }
            )
        }

    }
}

@Composable
fun PreferenceCategoryTitle(title: String, color: Color) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 14.sp,
            color = color,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceTitle(title: String, titleColor: Color) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 16.sp,
            color = titleColor,
            fontWeight = FontWeight.Medium
        ),
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceSummary(summary: String, summaryColor: Color) {
    Text(
        text = summary,
        style = TextStyle(
            fontSize = 14.sp,
            color = summaryColor,
            fontWeight = FontWeight.Light
        ),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PreferenceIcon(icon: ImageVector, iconColor: Color) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = icon,
            contentDescription = "dp",
            tint = iconColor
        )
    }
}

@Composable
fun PreferenceIcon(preferenceIcon: PreferenceIcon) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = IconResolver.getBitmap(icon = preferenceIcon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
    }
}
