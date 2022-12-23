package com.cb.cb_permission.presentation.composable.welcome

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.cb.cbtools.composables.CbAlertDialog

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
                CbAlertDialog(
                    showAlert = showSkipAlert,
                    onPositiveClick = {
                        showSkipAlert.value = false
                        PermissionUtil.skipPermission(
                            currentPermission = currentPermission,
                            context = context
                        )
                        onclickSkip()
                    },
                    title = "Skip Optional permission",
                    body = {
                        Text(
                            text = "Some feature might not work as expected",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    positiveText = "Proceed anyway"
                )
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