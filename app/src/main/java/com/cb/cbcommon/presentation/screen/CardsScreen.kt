package com.cb.cbcommon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbNoResult
import com.cb.cbtools.presentation.common.ErrorInfoCard
import com.cb.cbtools.presentation.common.IconInfo
import com.cb.cbtools.presentation.common.InfoCard

@Composable
fun CardsScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            CbAppBar(
                title = "Cards",
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
                ErrorInfoCard(
                    message = "*This is error card",
                    containerColor = MaterialTheme.colorScheme.background
                )
                InfoCard(
                    message = "This is info card",
                    textColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background
                )
                CbNoResult(
                    text = "No result found"
                )
                IconInfo("Click on ", Icons.Default.Save, " Icon to save changes")

            }
        }
    }
}

