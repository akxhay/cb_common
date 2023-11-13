package com.cb.cbcommon.presentation.page

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemTitle
import com.cb.cbtools.presentation.common.CbRadioGroup
import com.cb.cbtools.presentation.common.CbSearchBarUI
import com.cb.cbtools.presentation.common.CbTextDropDown

@ExperimentalAnimationApi
@Composable
fun InputScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val searchText = rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            CbAppBar(
                title = "CB inputs",
                backAction = { navController.navigateUp() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(state = scrollState)
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
                    requestFocus = false,
                    validation = {
                        true
                    },
                    validationMessage = ""
                )

                CbListItem(titleUnit = { CbListItemTitle(text = "Searched text is " + searchText.value) })


                val options = listOf("Disable", "Enable", "Test")
                val selectedOption = remember { mutableIntStateOf(1) }


                CbRadioGroup(
                    selectedOption.intValue,
                    options,
                ) {
                    selectedOption.intValue = options.indexOf(it)
                }
                CbListItem(titleUnit = { CbListItemTitle(text = "Selected item is " + options[selectedOption.intValue]) })


                val items = listOf("1", "2", "3")
                val selectedItem = remember { mutableStateOf("1") }

                CbListItem(
                    titleUnit = { CbListItemTitle(text = "Chosen value is " + selectedItem.value) },
                    iconUnit = {
                        CbTextDropDown(
                            modifier = Modifier.width(50.dp),
                            label = "Type",
                            options = items,
                            selectedOption = selectedItem.value,
                            onValueChange = {
                                selectedItem.value = it
                            }
                        )
                    }
                )
            }
        }
    }

}
