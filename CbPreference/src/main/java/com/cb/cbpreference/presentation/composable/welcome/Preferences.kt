package com.cb.cbpreference.presentation.composable.welcome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cb_permission.constants.PreferenceType

@Composable
fun PreferenceCategory(modifier: Modifier, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(50.dp))
            PreferenceCategoryTitle(title)
        }
    }

}

@Composable
fun Preference(
    modifier: Modifier,
    title: String,
    summary: String? = null,
    icon: ImageVector,
    preferenceType: PreferenceType = PreferenceType.DEFAULT,
    onclick: () -> Unit
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
            PreferenceIcon(icon)

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(3.0f, true)) {
                PreferenceTitle(title)
                summary?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    PreferenceSummary(it)
                }
            }
            if (preferenceType != PreferenceType.DEFAULT) {
                Column(
                    modifier = Modifier.weight(1.0f, true),
                    horizontalAlignment = Alignment.End
                ) {
                    PreferenceAction(
                        preferenceType
                    )

                }
            }
        }
    }

}

@Composable
fun PreferenceAction(preferenceType: PreferenceType) {
    Box(
        modifier = Modifier
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        if (preferenceType == PreferenceType.SWITCH)
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Green
            )
    }

}

@Composable
fun PreferenceCategoryTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceSummary(summary: String) {
    Text(
        text = summary,
        style = TextStyle(
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.inversePrimary,
            fontWeight = FontWeight.Light
        ),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PreferenceIcon(icon: ImageVector) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = icon,
            contentDescription = "dp",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
