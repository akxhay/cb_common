package com.cb.cbtools.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.R


@Composable
fun CbTextInputWithError(
    modifier: Modifier = Modifier,
    input: String,
    onInputChanged: (String) -> Unit,
    validation: (String) -> Boolean,
    validationMessage: String,
    label: String,
    horizontalPadding: Dp = 0.dp,
    onClearClick: () -> Unit,
    maxLines: Int = 6,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSearchBar: Boolean = false,
    requestFocus: Boolean = false,
    inputTextFocusedColor: Color = MaterialTheme.colorScheme.primary,
    inputTextUnFocusedColor: Color = MaterialTheme.colorScheme.secondary,
    inputTextCursorColor: Color = MaterialTheme.colorScheme.onSurface,
    inputTextErrorColor: Color = MaterialTheme.colorScheme.error,
    inputTextContentColor: Color = MaterialTheme.colorScheme.onSurface,
    inputTextPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    drawColor: Color = Color.Transparent,

    ) {
    val isValid = validation(input)
    var showClearButton by remember { mutableStateOf(false) }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    if (requestFocus) {
        LaunchedEffect(Unit) {
            (input.isEmpty())
            focusRequester.requestFocus()
        }
    }


    Column(modifier = modifier) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .padding(horizontal = horizontalPadding)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester)
                .drawBehind {
                    val strokeWidth = Stroke.DefaultMiter
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        drawColor,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                },
            shape = RoundedCornerShape(12.dp),

            value = input,
            onValueChange = {
                onInputChanged(it)
            },
            isError = !isValid,
            label = {
                Text(
                    text = label,
                    color = inputTextPlaceholderColor
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                autoCorrect = true,
            ),
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController?.hide()
            }),
            trailingIcon = {
                AnimatedVisibility(
                    visible = input.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        onClearClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear",
                            tint = inputTextContentColor

                        )
                    }

                }
            },
            leadingIcon = if (isSearchBar) {
                ({
                    IconButton(onClick = { }, enabled = false) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
                            tint = inputTextContentColor
                        )
                    }
                })
            } else null,
            maxLines = maxLines,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (!isValid) inputTextErrorColor else inputTextFocusedColor,
                unfocusedBorderColor = if (!isValid) inputTextErrorColor else inputTextUnFocusedColor,
                cursorColor = inputTextCursorColor,
                focusedTextColor = inputTextContentColor,
            ),
        )

        if (!isValid) {
            Text(
                text = validationMessage,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


@Composable
fun CbTextInputBasic(
    modifier: Modifier = Modifier,
    trailingIcon: @Composable() (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search),
    isSearchbar: Boolean,
    searchIconTint: Color = MaterialTheme.colorScheme.surface,
    inputTextPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant

) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = MaterialTheme.typography.bodyMedium,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearchbar)
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = searchIconTint,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty())
                        Text(
                            hint,
                            style = MaterialTheme.typography.bodyMedium,
                            color = inputTextPlaceholderColor

                        )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CbTextDropDown(
    label: String,
    modifierText: Modifier = Modifier.width(100.dp),
    modifierBox: Modifier = Modifier,
    selectedOption: String,
    options: Array<String>,
    useTextFiled: Boolean = false,
    onValueChange: (String) -> Unit,
    inputTextContentColor: Color = MaterialTheme.colorScheme.onSurface,

    inputTextPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        modifier = modifierBox,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }

    ) {
        if (useTextFiled) {
            TextField(
                modifier = Modifier
                    .menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                label = { Text(label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                textStyle = MaterialTheme.typography.bodySmall,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextContentColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
            )
        } else {
            Text(
                modifier = modifierText
                    .menuAnchor(),
                text = "$selectedOptionText ▼",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        onValueChange(selectionOption)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = selectionOption,
                            style = MaterialTheme.typography.bodyMedium,
                            color = inputTextPlaceholderColor
                        )
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun previewCbTextInputWithError() {
    CbTextInputWithError(
        input = "text",
        onInputChanged = {
        },
        validation = { input ->
            input.isNotBlank() // Implement your validation logic here
        },
        validationMessage = "Field cannot be blank",
        label = "Text Field Label",
        onClearClick = {}
    )
}

