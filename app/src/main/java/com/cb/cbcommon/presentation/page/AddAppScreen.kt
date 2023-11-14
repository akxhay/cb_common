package com.cb.cbcommon.presentation.page

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.viewModel.AppViewModel
import com.cb.cbtools.dto.AppSearchModelState
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemActionCheckBox
import com.cb.cbtools.presentation.common.CbListItemIconDrawablePrimary
import com.cb.cbtools.presentation.common.CbListItemTitle
import com.cb.cbtools.presentation.common.CbNoResult
import com.cb.cbtools.presentation.common.CbSearchBarUI

@ExperimentalAnimationApi
@Composable
fun AddAppScreen(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val checkedApp by viewModel.checkedApp.collectAsState(emptySet())
    val appSearchModelState by viewModel.appSearchModelState.collectAsState(AppSearchModelState.Empty)

    Scaffold(
        topBar = {
            CbAppBar(
                title = "Choose app to Monitor",
                backAction = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, checkedApp.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }) {
                        Icon(
                            Icons.Default.Save,
                            "Save",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
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
                    searchText = appSearchModelState.searchText,
                    placeholderText = "Search app",
                    onSearchTextChanged = { viewModel.onSearchTextChange(it) },
                    onClearClick = { viewModel.onClearClick() }
                )
                if (appSearchModelState.progressBar) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else if (appSearchModelState.apps.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                    ) {
                        items(appSearchModelState.apps) { app ->
                            CbListItem(
                                titleUnit = { CbListItemTitle(text = app.name) },
                                iconUnit = {
                                    CbListItemIconDrawablePrimary(
                                        drawable = BaseApplication.getInstance().appInfoService.appIconLookup(
                                            app.pkg
                                        )
                                    ) {
                                    }
                                },
                                actionUnit = {
                                    CbListItemActionCheckBox(
                                        state = checkedApp.contains(app.pkg),
                                        onChange = {
                                            viewModel.updateApp(app.pkg)
                                        }
                                    )
                                },
                                onClick = {
                                    viewModel.updateApp(app.pkg)
                                },
                            )
                        }
                    }
                } else {
                    CbNoResult(
                        text = "No match found"
                    )
                }
            }
        }
    }
}
