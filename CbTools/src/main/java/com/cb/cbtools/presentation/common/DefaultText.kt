package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ErrorInfoCard(message: String?, errorColor: Color = MaterialTheme.colorScheme.error) {
    if (message != null)
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = errorColor
                )
            },
            headlineContent = {

                Text(
                    color = errorColor,
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        )
}

@Composable
fun ErrorCard(
    message: String?,
    containerColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    onIconClick: () -> Unit,
) {
    if (message != null)
        ListItem(
            colors = ListItemDefaults.colors(containerColor = containerColor),
            headlineContent = {
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
            trailingContent = {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "VisibilityOff",
                        tint = containerColor
                    )
                }
            }

        )
}


@Composable
fun InfoCard(message: String?, textColor: Color = MaterialTheme.colorScheme.onBackground) {
    if (message != null)
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error",
                    tint = textColor
                )
            },
            headlineContent = {
                Text(
                    color = textColor,
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
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