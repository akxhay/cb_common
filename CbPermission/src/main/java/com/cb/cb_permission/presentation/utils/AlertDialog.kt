package com.cb.cb_permission.presentation.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ShowAlert(
    text: String?,
    title: String,
    confirmButtonText: String = "Confirm",
    showAlert: MutableState<Boolean>,
    onClick: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { showAlert.value = false },
        confirmButton = {
            TextButton(onClick = {
                onClick()
                showAlert.value = false
            })
            { Text(text = confirmButtonText) }
        },
        dismissButton = {
            TextButton(onClick = {
                showAlert.value = false
            })
            { Text(text = "Cancel") }
        },
        title = { Text(text = title, style = TextStyle(color = Color.Black, fontSize = 17.sp)) },
        text = {
            text?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Gray, fontSize = 13.sp)
                )
            }
        }
    )
}