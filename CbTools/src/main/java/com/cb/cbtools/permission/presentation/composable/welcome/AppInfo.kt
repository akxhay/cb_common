package com.cb.cbtools.permission.presentation.composable.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun WelcomeInfoText(
    text: String,
    style: TextStyle,
    color: Color
) {
    Box(
        Modifier
            .fillMaxWidth(),
        Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = style,
            color = color
        )
    }
}

@Composable
fun WelcomeInfoSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}

@Composable
fun WelcomeInfoIcon(
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
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
