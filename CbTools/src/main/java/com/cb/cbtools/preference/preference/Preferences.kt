package com.cb.cbtools.preference.preference

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cbtools.dynamic.constants.ActionType
import com.cb.cbtools.dynamic.data.DynamicIcon
import com.cb.cbtools.dynamic.util.IconResolver


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
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

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
fun PreferenceIcon(dynamicIcon: DynamicIcon) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = IconResolver.getBitmap(icon = dynamicIcon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
    }
}
