package com.cb.cbtools.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CbSearchBarUI(
    searchText: String,
    placeholderText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    requestFocus: Boolean = false,
    validation: (String) -> Boolean,
    validationMessage: String,
) {

    Box {
        Column(
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .background(
                    color = backgroundColor
                )
        ) {

            CbTextInputWithError(
                modifier = Modifier,
                label = placeholderText,
                input = searchText,
                onClearClick = onClearClick,
                isSearchBar = true,
                maxLines = 1,
                requestFocus = requestFocus,
                onInputChanged = onSearchTextChanged,
                validation = validation,
                validationMessage = validationMessage,
                horizontalPadding = 10.dp,
            )
        }
    }
}

@Composable
@Preview
fun previewCbSearchBarUI() {
    CbSearchBarUI(
        searchText = "text",
        placeholderText = "Search recipients",
        onSearchTextChanged = { },
        onClearClick = { },
        requestFocus = false,
        validationMessage = "",
        validation = {
            "".isNotBlank()
        }
    )
}
