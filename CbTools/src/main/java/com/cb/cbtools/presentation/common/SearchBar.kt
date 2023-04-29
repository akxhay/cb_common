package com.cb.cbtools.presentation.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CbSearchBarUI(
    searchText: String,
    placeholderText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    requestFocus: Boolean = false
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
                inputString = searchText,
                onValueChange = onSearchTextChanged,
                onClearClick = onClearClick,
                isSearchBar = true,
                maxLines = 1,
                usingMutableState = false,
                requestFocus = requestFocus
            )
        }
    }
}

