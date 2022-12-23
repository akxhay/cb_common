package com.cb.cbtools.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CbAlertDialog(
    showAlert: MutableState<Boolean>,
    onPositiveClick: () -> Unit,
    title: String,
    body: @Composable (() -> Unit),
    positiveText: String = "Ok",
    negativeText: String = "Cancel"


) {
    AlertDialog(
        onDismissRequest = { showAlert.value = false },
        confirmButton = {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
            {
                TextButton(
                    onClick =
                    onPositiveClick
                )
                {
                    Text(
                        text = positiveText,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { showAlert.value = false })
            {
                Text(
                    text = negativeText,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },

        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

            }
        },
        text = body
    )
}