package com.cb.cbtools.presentation.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun PopUpInput(
    title: String,
    input: String,
    invalidSet: Set<String> = HashSet(),
    dismiss: () -> Unit,
    onChange: (String) -> Unit,
) {
    val text: MutableState<String> = remember { mutableStateOf(input) }
    val context = LocalContext.current
    val error: MutableState<String?> = remember {
        mutableStateOf(
            if (input.isEmpty()) {
                "text cannot be empty"
            } else {
                null
            }
        )
    }

    val onValueChange: (String) -> Unit = {
        text.value = it
        error.value = if (it.trim().isEmpty()) {
            "text cannot be empty"
        } else if (invalidSet.contains(it.trim())) {
            "text is invalid"
        }  else {
            null
        }

    }
    val onClearClick: () -> Unit = {
        onValueChange("")
    }
    CbGenericDialog(
        onDismissClick = {
            dismiss()
        }, onConfirmClick = {
            if (error.value.isNullOrBlank()) {
                onChange(text.value.trim())
            } else {
                Toast.makeText(context, error.value, Toast.LENGTH_SHORT).show()
            }
        },
        title = title,
        text = {
            CbTextInputWithError(
                modifier = Modifier,
                label = title,
                input = text,
                error = error,
                horizontalPadding = 10.dp,
                onValueChange = onValueChange,
                onClearClick = onClearClick,
                requestFocus = true

            )
        },
        confirmText = "Proceed",
        showDivider = false

    )
}
