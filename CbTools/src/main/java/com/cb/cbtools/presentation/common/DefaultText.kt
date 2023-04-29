package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ErrorInfoCard(message: String?, errorColor: Color = MaterialTheme.colorScheme.error) {
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
            actionImageVector = {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "VisibilityOff",
                        tint = containerColor
                    )
                }
            },
            containerColor = containerColor
        )
}


@Composable
fun InfoCard(message: String?, textColor: Color = MaterialTheme.colorScheme.onBackground) {
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
        )
}

@Composable
fun CbNoResult(
    text: String = "No matches found",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
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
            })
    }

}