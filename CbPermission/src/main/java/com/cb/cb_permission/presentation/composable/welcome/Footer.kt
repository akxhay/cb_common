package com.cb.cb_permission.presentation.composable.common

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cb_permission.constants.ConstantSetUp
import com.cb.cb_permission.presentation.utils.PermissionUtil
import com.cb.cb_permission.presentation.utils.ShowAlert

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
        modifier = Modifier
            .fillMaxWidth(),
        visible = ConstantSetUp.canPermissionSkipped()[currentPermission] == true,
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
        val showSkipAlert = remember {
            mutableStateOf(false)
        }
        Column(
            Modifier
                .fillMaxWidth()
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
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                showSkipAlert.value = true
                            },
                        text = "Skip >>",
                        style = TextStyle(
                            color = skipButtonTextColor,
                            textDecoration = TextDecoration.Underline,
                        ),
                    )
                }
            }


            if (showSkipAlert.value) {
                ShowAlert(
                    text = "Some feature might not work as expected",
                    title = "Skip Optional permission",
                    confirmButtonText = "Proceed anyway",
                    showAlert = showSkipAlert
                ) {
                    PermissionUtil.skipPermission(
                        currentPermission = currentPermission,
                        context = context
                    )
                    onclickSkip()
                }
            }
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