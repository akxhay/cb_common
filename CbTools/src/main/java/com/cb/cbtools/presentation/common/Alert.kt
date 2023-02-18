package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.cb.cbtools.dynamic.DynamicConfig


@Composable
fun CbGenericDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    title: String,
    text: @Composable () -> Unit,
    confirmText: String = "Ok",
    dismissText: String = "Cancel",
    showDivider: Boolean = true,
    dynamicConfig: DynamicConfig
) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        containerColor = dynamicConfig.getAlertBackgroundColor(),
        onDismissRequest = { onDismissClick() },
        confirmButton = {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = dynamicConfig.getAlertConfirmButtonColor(),
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
                        color = dynamicConfig.getAlertConfirmTextColor(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissClick()
            })
            {
                Text(
                    text = dismissText,
                    color = dynamicConfig.getAlertDismissTextColor(),
                    style = MaterialTheme.typography.titleSmall

                )
            }
        },

        title = {
            Column() {
                AlertTitleText(text = title, color = dynamicConfig.getAlertTitleColor())
                Spacer(modifier = Modifier.height(10.dp))
                if (showDivider)
                    Divider(color = dynamicConfig.getAlertDividerColor(), thickness = 1.dp)
            }
        },
        text = {
            text()
        }
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
    dynamicConfig: DynamicConfig
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
                    color = dynamicConfig.getAlertContentColor(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        dynamicConfig = dynamicConfig
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
    dynamicConfig: DynamicConfig
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
                    color = dynamicConfig.getAlertContentColor(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        dynamicConfig = dynamicConfig
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