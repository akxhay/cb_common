package com.cb.cbtools.permission.presentation.composable.welcome

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.composables.CbDecisionDialog
import com.cb.cbtools.permission.constants.ConstantSetUp
import com.cb.cbtools.permission.presentation.utils.PermissionUtil

@Composable
fun Footer(
    context: Activity,
    currentPermission: String,
    onclickSkip: () -> Unit,
    dynamicConfig: DynamicConfig
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
                            color = dynamicConfig.getWelcomeScreenSkipTextColor(),
                            textDecoration = TextDecoration.Underline,
                        ),
                    )
                }
            }
        }
        if (showSkipAlert.value) {
            CbDecisionDialog(
                showAlert = showSkipAlert,
                onConfirmClick = {
                    PermissionUtil.skipPermission(
                        currentPermission = currentPermission,
                        context = context
                    )
                    onclickSkip()
                },
                title = "Skip Optional permission",
                text = "Some feature might not work as expected",
                confirmText = "Proceed anyway",
                dynamicConfig = dynamicConfig
            )
        }
    }
}