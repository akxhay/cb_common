package com.cb.cbtools.permission.presentation.utils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cbtools.permission.constants.ConstantSetUp
import com.cb.cbtools.permission.presentation.composable.welcome.PermissionDialog


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionButton(
    context: Activity,
    visiblePermission: MutableState<Boolean>,
    currentPermission: String,
    modifier: Modifier,
    permissionCardBackground: Color,
    permissionTextColor: Color,
    appName: String
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
                    backgroundColor = permissionCardBackground,
                    elevation = 20.dp,
                    shape = RoundedCornerShape(12.dp),
                )
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .fillMaxWidth()
                                .height(42.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

//                            Image(
//                                painterResource(ConstantSetUp.getPermissionDrawable()[currentPermission]!!),
//                                contentDescription = "",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier.size(30.dp)
//                            )

                            Icon(
                                imageVector = ConstantSetUp.getPermissionIcon()[currentPermission]!!,
                                contentDescription = "icon",
                                tint = permissionTextColor
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(7.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp),
                                text = ConstantSetUp.getPermissionButtonText()[currentPermission].toString(),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = permissionTextColor,
                                    fontSize = 15.sp
                                ),
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
                )
            }
        }

    }
}