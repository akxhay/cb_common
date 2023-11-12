package com.cb.cbcommon.presentation.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbSearchBarUI

@ExperimentalAnimationApi
@Composable
fun SearchBarScreen(
    navController: NavController
) {
    val searchText = rememberSaveable() {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            CbAppBar(
                title = "Search bar",
                backAction = { navController.navigateUp() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
            )
            {
                Spacer(modifier = Modifier.height(10.dp))
                CbSearchBarUI(
                    searchText = searchText.value,
                    placeholderText = "Search",
                    onSearchTextChanged = { searchText.value = it },
                    onClearClick = { searchText.value = "" },
                    requestFocus = true,
                    validation = {
                        true
                    },
                    validationMessage = ""
                )
                Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                    Text(text = "Searched text is " + searchText.value)
                }

            }
        }
    }
}

