package com.cb.cb_permission.presentation.composable.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppNameBox(
    appName: String,
    appNameColor: Color
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(30.dp),
        Alignment.Center
    ) {
        Text(
            text = appName,
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = appNameColor,
                fontSize = 20.sp
            ),
        )
    }
}

@Composable
fun AppDescBox(
    appDesc: String,
    appDescColor: Color
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(30.dp),
        Alignment.Center
    ) {
        Text(
            text = appDesc,
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = appDescColor,
                fontSize = 20.sp
            ),
        )
    }
}

@Composable
fun AppIconBox(
    @DrawableRes appIcon: Int
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(150.dp),
        Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            elevation = 2.dp
        ) {
            Image(
                painterResource(appIcon),
                contentDescription = "appIcon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
