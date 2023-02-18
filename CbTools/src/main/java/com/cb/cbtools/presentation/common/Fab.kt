package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cb.cbtools.dynamic.DynamicConfig


@Composable
fun CbFab(
    dynamicConfig: DynamicConfig,
    icon: ImageVector = Icons.Default.Add,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        FloatingActionButton(
            onClick = onClick,
            backgroundColor = dynamicConfig.getFloatingBackgroundColor(),
            contentColor = dynamicConfig.getFloatingContentColor()
        ) {
            Icon(icon, "icon", tint = dynamicConfig.getFloatingContentColor())
        }

    }
}

