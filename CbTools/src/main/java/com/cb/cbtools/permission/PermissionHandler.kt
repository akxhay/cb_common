package com.cb.cbtools.permission

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cb.cbtools.composables.permission.welcome.PermissionDialog
import com.cb.cbtools.composables.permission.welcome.WelcomeInfoText
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.permission.permission_handler.factory.PermissionHandlerFactory

const val animationDuration = 2000

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionButton(
    context: Activity,
    appName: String,
    modifier: Modifier,
    dynamicConfig: DynamicConfig,
    currentPermission: CbPermission
) {

    val showPermissionAlert = remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier,
            Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentPermission,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = animationDuration)) with
                            fadeOut(animationSpec = tween(durationMillis = animationDuration))
                }
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
                                    text = PermissionHandlerFactory.getHandlerForPermission(
                                        it.permissionType
                                    )
                                    !!.getPermissionButtonText(),
                                    color = dynamicConfig.getWelcomeScreenCardContentColor(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            leadingContent = ({
                                Icon(
                                    imageVector = PermissionHandlerFactory.getHandlerForPermission(
                                        it.permissionType
                                    )
                                    !!.getPermissionIcon(),
                                    contentDescription = "icon",
                                    tint = dynamicConfig.getWelcomeScreenCardContentColor()
                                )
                            })
                        )
                    }
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