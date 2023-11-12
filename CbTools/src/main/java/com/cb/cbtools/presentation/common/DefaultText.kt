package com.cb.cbtools.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun ErrorInfoCard(
    message: String?,
    errorColor: Color = MaterialTheme.colorScheme.error,
    containerColor: Color = MaterialTheme.colorScheme.onErrorContainer,
) {
    if (message != null)
        CbListItem(
            titleUnit = {
                Text(
                    color = errorColor,
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            },
            iconUnit = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = errorColor
                )
            },
            containerColor = containerColor

        )
}

@Composable
fun ErrorCard(
    message: String?,
    containerColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    onIconClick: () -> Unit,
) {
    if (message != null)
        CbListItem(
            titleUnit = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        color = containerColor,
                        textAlign = TextAlign.Center,
                        text = "$message!",
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            },
            actionUnit = {
                CbListItemActionCustom {
                    IconButton(onClick = { onIconClick() }) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "VisibilityOff",
                            tint = containerColor
                        )
                    }
                }
            },

            containerColor = containerColor
        )
}


@Composable
fun InfoCard(
    message: String?,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    containerColor: Color = MaterialTheme.colorScheme.background,
) {
    if (message != null)
        CbListItem(
            titleUnit = {
                Text(
                    color = textColor,
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            },
            iconUnit = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error",
                    tint = textColor
                )
            },
            containerColor = containerColor
        )
}

@Composable
fun CbNoResult(
    text: String = "No matches found",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    containerColor: Color = MaterialTheme.colorScheme.background,
    wholePage: Boolean = true
) {
    Column(
        modifier = if (wholePage) Modifier.fillMaxSize() else Modifier,
    ) {
        CbListItem(
            titleUnit = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    overflow = TextOverflow.Ellipsis
                )
            },
            containerColor = containerColor
        )
    }

}

@Composable
fun IconInfo(
    text1: String,
    icon: ImageVector,
    text2: String,
    background: Color = MaterialTheme.colorScheme.background,
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text1,

            style = TextStyle(
                fontSize = 15.sp,
                color = color
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = icon,
            contentDescription = "save",
            tint = color
        )
        Text(
            textAlign = TextAlign.Center,
            text = text2,

            style = TextStyle(
                fontSize = 15.sp,
                color = color
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}