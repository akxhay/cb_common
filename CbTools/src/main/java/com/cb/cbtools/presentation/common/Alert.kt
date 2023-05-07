package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun CbGenericDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    title: String,
    text: @Composable () -> Unit,
    confirmText: String = "Ok",
    dismissText: String? = "Cancel",
    showDivider: Boolean = true,
    alertBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    alertConfirmButtonColor: Color = MaterialTheme.colorScheme.primary,
    alertConfirmTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    alertDismissTextColor: Color = MaterialTheme.colorScheme.onSurface,
    alertDividerColor: Color = MaterialTheme.colorScheme.primary,
    alertTitleColor: Color = MaterialTheme.colorScheme.onSurface,

    ) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        containerColor = alertBackgroundColor,
        onDismissRequest = { onDismissClick() },
        confirmButton = {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = alertConfirmButtonColor,
                ),
            )
            {
                TextButton(
                    onClick = {
                        //may not be required in case of validation
//                        showAlert.value = false
                        onConfirmClick()
                    }
                )
                {
                    Text(
                        text = confirmText,
                        color = alertConfirmTextColor,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        dismissButton = {
            if (dismissText != null) {
                TextButton(onClick = {
                    onDismissClick()
                })
                {
                    Text(
                        text = dismissText,
                        color = alertDismissTextColor,
                        style = MaterialTheme.typography.titleSmall

                    )
                }
            }
        },

        title = {
            Column {
                AlertTitleText(
                    text = title,
                    color = alertTitleColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (showDivider)
                    Divider(color = alertDividerColor, thickness = 1.dp)
            }
        },
        text = {
            text()
        }
    )
}

@Composable
fun CbInfoDialog(
    onConfirmClick: () -> Unit,
    title: String,
    text: @Composable () -> Unit,
    confirmText: String = "Ok",
    alertBackgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    CbGenericDialog(
        onConfirmClick = onConfirmClick,
        title = title,
        confirmText = confirmText,
        dismissText = null,
        text = { text() },
        alertBackgroundColor = alertBackgroundColor,
        onDismissClick = {}
    )

}


@Composable
fun CbDecisionDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    title: String,
    text: String,
    confirmText: String = "Ok",
    dismissText: String = "Cancel",
    alertContentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    CbGenericDialog(
        onConfirmClick = onConfirmClick,
        onDismissClick = onDismissClick,
        title = title,
        confirmText = confirmText,
        dismissText = dismissText,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    color = alertContentColor,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
    )

}

@Composable
fun CbDoubleDecisionDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    title: String,
    text: String,
    confirmText: String = "Ok",
    dismissText: String = "Cancel",
    alertContentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    CbGenericDialog(
        onConfirmClick = onConfirmClick,
        onDismissClick = onDismissClick,
        title = title,
        confirmText = confirmText,
        dismissText = dismissText,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    color = alertContentColor,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )

}

@Composable
fun AlertTitleText(
    text: String,
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
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}