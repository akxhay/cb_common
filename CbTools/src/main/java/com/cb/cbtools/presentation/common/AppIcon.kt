package com.cb.cbtools.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.service.AppInfoService
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun AppIcon(appPackageName: String, appInfoService: AppInfoService, size: Dp = 50.dp) {
    val appIcon = rememberDrawablePainter(
        drawable = appInfoService.appIconLookup(
            appPackageName
        )
    )
    Image(
        painter = appIcon,
        contentScale = ContentScale.Crop,
        contentDescription = "App Icon",
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}
