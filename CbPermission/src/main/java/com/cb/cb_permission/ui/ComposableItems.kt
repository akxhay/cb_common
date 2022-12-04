package com.cb.cb_permission.ui

import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cb_permission.constants.ConstantSetUp.canPermissionSkipped
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionButtonText
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionDrawable
import com.cb.cb_permission.utils.Util
import com.cb.cb_permission.utils.Util.requestPermissionByType

object ComposableItems {

    @Composable
    fun Footer(
        context: Activity,

        currentPermission: String,
        onclickSkip: () -> Unit,
        skipButtonColor: Color,
        skipButtonTextColor: Color
    ) {
        val density = LocalDensity.current

        AnimatedVisibility(
            visible = canPermissionSkipped()[currentPermission] == true,
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
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    SmallButton(
                        Modifier,
                        "Skip >>",
                        onClick = {
                            Util.skipPermission(
                                currentPermission = currentPermission,
                                context = context
                            )
                            onclickSkip()
                        },
                        skipButtonBackground = skipButtonColor,
                        skipButtonTextColor = skipButtonTextColor
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
//            Row(
//                horizontalArrangement = Arrangement.End,
//            ) {
//                SmallButton(Modifier, "next", onclickNext, nextButtonColor)
//            }

            }
        }
    }

    @Composable
    fun SmallButton(
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit,
        skipButtonBackground: Color,
        skipButtonTextColor: Color
    ) {

        Box(
            modifier
                .size(100.dp, 50.dp),
            Alignment.Center
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = skipButtonBackground),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = text,
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        color = skipButtonTextColor,
                        fontSize = 20.sp
                    ),
                )
            }
        }
    }


    @Composable
    fun Space(count: Int) {
        LazyColumn {
            items(count = count) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }
    }

    @Composable
    fun AppNameBox(
        appName: String,
        appNameColor: Color
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(30.dp),
            Alignment.Center
        ) {
            Text(
                text = appName,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = appNameColor,
                    fontSize = 20.sp
                ),
            )
        }
    }

    @Composable
    fun AppDescBox(
        appDesc: String,
        appDescColor: Color
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(30.dp),
            Alignment.Center
        ) {
            Text(
                text = appDesc,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = appDescColor,
                    fontSize = 20.sp
                ),
            )
        }
    }

    @Composable
    fun AppIconBox(
        @DrawableRes appIcon: Int
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            Alignment.Center
        ) {
            Card(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                elevation = 2.dp
            ) {
                Image(
                    painterResource(appIcon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun PermissionButton(
        context: Activity,
        visiblePermission: MutableState<Boolean>,
        currentPermission: String,
        modifier: Modifier,
        permissionCardBackground: Color,
        permissionTextColor: Color
    ) {
        val density = LocalDensity.current

        Box(
            modifier,
            Alignment.Center
        ) {
            AnimatedVisibility(
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                        .clickable {
                            requestPermissionByType(
                                context,
                                currentPermission
                            )
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

                            Image(
                                painterResource(getPermissionDrawable()[currentPermission]!!),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(7.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp),
                                text = getPermissionButtonText()[currentPermission].toString(),
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
        }
    }
}