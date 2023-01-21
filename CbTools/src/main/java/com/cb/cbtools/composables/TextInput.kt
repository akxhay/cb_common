package com.cb.cbtools.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbcommon.DynamicConfig


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CBTextInputWithError(
    label: String,
    input: MutableState<String>,
    error: MutableState<String?>,
    horizontalPadding: Dp = 0.dp,
    onValueChange: (String) -> Unit,
    maxLines: Int = 6,
    modifier: Modifier = Modifier,
    dynamicConfig: DynamicConfig
) {
    val focusedBorderColor = dynamicConfig.getInputTextFocusedColor()
    val unfocusedBorderColor = dynamicConfig.getInputTextUnFocusedColor()
    val cursorColor = dynamicConfig.getInputTextCursorColor()
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf(input.value) }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            shape = RoundedCornerShape(12.dp),
            value = text,

            onValueChange = { newText ->
                text = newText
                onValueChange(newText)
            },
            singleLine = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (!error.value.isNullOrBlank()) Color.Red else focusedBorderColor,
                unfocusedBorderColor = if (!error.value.isNullOrBlank()) Color.Red else unfocusedBorderColor,
                cursorColor = cursorColor,
                textColor = dynamicConfig.getInputTextContentColor(),
            ),
            placeholder = {
                Text(
                    text = label,
                    color = dynamicConfig.getInputTextPlaceholderColor()
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                autoCorrect = true,
            ),
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController?.hide()
            }),
            trailingIcon = {
                IconButton(onClick = {
                    text = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        tint = dynamicConfig.getInputTextContentColor()
                    )
                }
            },
            maxLines = maxLines
        )
    }
}

