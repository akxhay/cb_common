package com.cb.cbtools.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputWithError(
    label: String,
    input: MutableState<String>,
    error: MutableState<String?>,
    horizontalPadding: Dp = 0.dp,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    modifier: Modifier
) {
    var text by remember { mutableStateOf(TextFieldValue(input.value)) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            value = text,
            onValueChange = { newText ->
                text = newText
                onValueChange(newText.text)
            },
            trailingIcon = {
                if (!error.value.isNullOrBlank())
                    Icon(
                        Icons.Filled.Error, "error",
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            maxLines = maxLines,
            isError = !error.value.isNullOrBlank(),
        )
        if (!error.value.isNullOrBlank()) {
            Text(
                text = error.value!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}