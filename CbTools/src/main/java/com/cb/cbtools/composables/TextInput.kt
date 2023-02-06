package com.cb.cbtools.composables

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
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.R
import com.cb.cbtools.dynamic.DynamicConfig


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CbTextInputWithError(
    modifier: Modifier = Modifier,
    label: String,
    inputString: String? = null,
    input: MutableState<String>? = null,
    error: MutableState<String?>? = null,
    horizontalPadding: Dp = 0.dp,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    maxLines: Int = 6,
    dynamicConfig: DynamicConfig,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSearchBar: Boolean = false,
    usingMutableState: Boolean = true,
    requestFocus: Boolean = false
) {
    var showClearButton by remember { mutableStateOf(false) }

    val focusedBorderColor = dynamicConfig.getInputTextFocusedColor()
    val unfocusedBorderColor = dynamicConfig.getInputTextUnFocusedColor()
    val cursorColor = dynamicConfig.getInputTextCursorColor()
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    if (requestFocus) {
        LaunchedEffect(Unit) {
            if ((inputString != null && inputString.isEmpty()) || (input != null && input.value.isEmpty()))
                focusRequester.requestFocus()
        }
    }
    val drawColor = dynamicConfig.getDividerColor()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
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
            value = if (usingMutableState) input!!.value else inputString!!,

            onValueChange = { newText ->
                onValueChange(newText)
            },
            singleLine = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (!error?.value.isNullOrBlank()) dynamicConfig.getInputTextErrorColor() else focusedBorderColor,
                unfocusedBorderColor = if (!error?.value.isNullOrBlank()) dynamicConfig.getInputTextErrorColor() else unfocusedBorderColor,
                cursorColor = cursorColor,
                textColor = dynamicConfig.getInputTextContentColor(),
            ),
            label = {
                Text(
                    text = label,
                    color = dynamicConfig.getInputTextPlaceholderColor()
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
                    visible = if (usingMutableState) input!!.value.isNotEmpty() else inputString!!.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        onClearClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear",
                            tint = dynamicConfig.getInputTextContentColor()

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
                            tint = dynamicConfig.getInputTextContentColor()
                        )
                    }
                })
            } else null,
            maxLines = maxLines
        )
    }
}


@Composable
fun CbTextInputBasic(
    modifier: Modifier = Modifier,
    trailingIcon: @Composable() (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search),
    dynamicConfig: DynamicConfig,
    isSearchbar: Boolean
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
                        tint = dynamicConfig.getAlertContentColor(),
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty())
                        Text(
                            hint,
                            style = MaterialTheme.typography.bodyMedium,
                            color = dynamicConfig.getInputTextPlaceholderColor()

                        )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CbTextDropDown(
    label: String,
    modifierText: Modifier = Modifier.width(100.dp),
    modifierBox: Modifier = Modifier,
    selectedOption: String,
    dynamicConfig: DynamicConfig,
    options: Array<String>,
    useTextFiled: Boolean = false,
    onValueChange: (String) -> Unit,

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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = dynamicConfig.getInputTextContentColor(),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
            )
        } else {
            Text(
                modifier = modifierText
                    .menuAnchor(),
                text = selectedOptionText,
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
                            color = dynamicConfig.getInputTextPlaceholderColor()
                        )
                    }
                )
            }
        }
    }
}


