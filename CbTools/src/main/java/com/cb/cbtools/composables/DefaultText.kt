package com.cb.cbtools.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.cb.cbtools.dynamic.DynamicConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorInfoCard(message: String?, dynamicConfig: DynamicConfig) {
    if (message != null)
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = dynamicConfig.getInputTextErrorColor()
                )
            },
            headlineText = {

                Text(
                    color = dynamicConfig.getInputTextErrorColor(),
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorCard(message: MutableState<String?>, dynamicConfig: DynamicConfig) {
    if (message.value != null)
        ListItem(
            colors = ListItemDefaults.colors(containerColor = dynamicConfig.getInputErrorContainerColor()),
            headlineText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        color = dynamicConfig.getTextOnErrorContainerColor(),
                        textAlign = TextAlign.Center,
                        text = message.value!! + "!",
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            },
            trailingContent = {
                IconButton(onClick = { message.value = null }) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "VisibilityOff",
                        tint = dynamicConfig.getTextOnErrorContainerColor()
                    )
                }
            }

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(message: String?, dynamicConfig: DynamicConfig) {
    if (message != null)
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error",
                    tint = dynamicConfig.getPrimaryTextOnBackGroundColor()
                )
            },
            headlineText = {

                Text(
                    color = dynamicConfig.getPrimaryTextOnBackGroundColor(),
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
    dynamicConfig: DynamicConfig,
    wholePage: Boolean = true
) {
    Column(
        modifier = if (wholePage) Modifier.fillMaxSize() else Modifier,
    ) {
        CbListItem(
            dynamicConfig = dynamicConfig,
            titleUnit = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = dynamicConfig.getPrimaryTextOnBackGroundColor(),
                    overflow = TextOverflow.Ellipsis
                )
            })
    }

}