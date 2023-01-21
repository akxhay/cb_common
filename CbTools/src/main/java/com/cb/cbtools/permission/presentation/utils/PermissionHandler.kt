package com.cb.cbtools.permission.presentation.utils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.permission.constants.ConstantSetUp
import com.cb.cbtools.permission.presentation.composable.welcome.PermissionDialog
import com.cb.cbtools.permission.presentation.composable.welcome.WelcomeInfoText


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionButton(
    context: Activity,
    appName: String,
    visiblePermission: MutableState<Boolean>,
    currentPermission: String,
    modifier: Modifier,
    dynamicConfig: DynamicConfig
) {
    val density = LocalDensity.current
    val showPermissionAlert = remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        modifier = modifier.fillMaxWidth(),
        visible = visiblePermission.value,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -10.dp.roundToPx() }
        } + expandHorizontally(
            // Expand from the top.
            expandFrom = Alignment.End
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()

    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier,
                Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                        .clickable {
                            showPermissionAlert.value = true
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                )
                {
                    ListItem(
                        colors = ListItemDefaults.colors(containerColor = dynamicConfig.getWelcomeScreenCardBackgroundColor()),
                        headlineText = {
                            WelcomeInfoText(
                                text = ConstantSetUp.getPermissionButtonText()[currentPermission].toString(),
                                color = dynamicConfig.getWelcomeScreenCardContentColor(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        leadingContent = ({
                            Icon(
                                imageVector = ConstantSetUp.getPermissionIcon()[currentPermission]!!,
                                contentDescription = "icon",
                                tint = dynamicConfig.getWelcomeScreenCardContentColor()
                            )
                        })

                    )
                }
            }
        }
        if (showPermissionAlert.value) {
            PermissionDialog(
                appName,
                context,
                currentPermission,
                showPermissionAlert,
                dynamicConfig
            )
        }

    }
}