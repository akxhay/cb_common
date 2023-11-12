package com.cb.cbtools.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun PopUpInput(
    title: String,
    label: String,
    input: String,
    dismiss: () -> Unit,
    onInputChanged: (String) -> Unit,
    validation: (String) -> Boolean,
    validationMessage: String,
    onConfirmClick: () -> Unit,
    onClearClick: () -> Unit
) {
    CbGenericDialog(
        onDismissClick = {
            dismiss()
        }, onConfirmClick = {
            onConfirmClick()
        },
        title = title,
        text = {
            CbTextInputWithError(
                input = input,
                onInputChanged = onInputChanged,
                validation = validation,
                validationMessage = validationMessage,
                label = label,
                onClearClick = onClearClick,
                horizontalPadding = 10.dp,
                requestFocus = true

            )
        },
        confirmText = "Proceed",
        showDivider = false

    )
}
