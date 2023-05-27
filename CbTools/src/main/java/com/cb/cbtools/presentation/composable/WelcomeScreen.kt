package com.cb.cbtools.presentation.composable

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.factory.PermissionHandlerFactory
import com.cb.cbtools.presentation.common.CbDecisionDialog
import com.cb.cbtools.presentation.viewModel.PermissionViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


const val animationDuration = 2000


@Composable
fun WelcomeScreen(
    context: Activity,
    appName: String,
    appDesc: String,
    version: String,
    @DrawableRes appIcon: Int,
    onclickSkip: () -> Unit,
    viewModel: PermissionViewModel,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    tertiaryTextColor: Color = MaterialTheme.colorScheme.primary,
    cardColor: Color = MaterialTheme.colorScheme.primary,
    cardTextColor: Color = MaterialTheme.colorScheme.onPrimary

) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            val currentPermission by viewModel.currentPermission.observeAsState(initial = null as CbPermission?)
            currentPermission?.let {
                PermissionScreen(
                    context = context,
                    appIcon = appIcon,
                    appName = appName,
                    appDesc = appDesc,
                    version = version,
                    onclickSkip = onclickSkip,
                    currentPermission = it,
                    backgroundColor = backgroundColor,
                    primaryTextColor = primaryTextColor,
                    secondaryTextColor = secondaryTextColor,
                    tertiaryTextColor = tertiaryTextColor,
                    cardColor = cardColor,
                    cardTextColor = cardTextColor
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionScreen(
    context: Activity,
    @DrawableRes appIcon: Int,
    appName: String,
    appDesc: String,
    version: String,
    onclickSkip: () -> Unit,
    currentPermission: CbPermission,
    backgroundColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    tertiaryTextColor: Color,
    cardColor: Color,
    cardTextColor: Color
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        AppInfo(
            appIcon = appIcon,
            appName = appName,
            appDesc = appDesc,
            version = version,
            primaryTextColor = primaryTextColor,
            secondaryTextColor = secondaryTextColor
        )
        PermissionButton(
            appName = appName,
            context = context,
            currentPermission = currentPermission,
            modifier = Modifier
                .weight(10f),
            cardColor = cardColor,
            cardTextColor = cardTextColor

        )
        Footer(
            context = context,
            onclickSkip = onclickSkip,
            skipColor = tertiaryTextColor,
            currentPermission = currentPermission
        )
    }
}

@Composable
fun AppInfo(
    @DrawableRes appIcon: Int,
    appName: String,
    appDesc: String,
    version: String,
    primaryTextColor: Color = MaterialTheme.colorScheme.onSurface,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Column {
        WelcomeInfoSpacer()
        WelcomeInfoIcon(appIcon = appIcon)
        WelcomeInfoSpacer()
        WelcomeInfoText(
            text = appName,
            color = primaryTextColor,
            style = MaterialTheme.typography.displaySmall
        )
        WelcomeInfoSpacer()
        WelcomeInfoText(
            text = appDesc,
            color = secondaryTextColor,
            style = MaterialTheme.typography.bodyLarge
        )
        WelcomeInfoSpacer()
        WelcomeInfoText(
            text = version,
            color = secondaryTextColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun WelcomeInfoText(
    text: String,
    style: TextStyle,
    color: Color
) {
    Box(
        Modifier
            .fillMaxWidth(),
        Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = style,
            color = color
        )
    }
}

@Composable
fun WelcomeInfoSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}

@Composable
fun WelcomeInfoIcon(
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
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Image(
                painterResource(appIcon),
                contentDescription = "appIcon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


private const val PACKAGE = "package"

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionDialog(
    appName: String,
    context: Activity,
    currentPermission: CbPermission,
    showPermissionAlert: MutableState<Boolean>,
) {
    val permissionHandler =
        PermissionHandlerFactory.getHandlerForPermission(currentPermission.permissionType)
    var action: () -> Unit? = { permissionHandler!!.askPermission(context) }
    val confirmButtonText = remember {
        mutableStateOf("Confirm")
    }

    if (permissionHandler!!.isSimplePermission() &&
        permissionHandler.getPermissionToCheck() != null
    ) {
        val permissionState = rememberPermissionState(
            permissionHandler.getPermissionToCheck()!!
        )
        if (!permissionState.status.isGranted) {
            if (permissionState.status.shouldShowRationale) {
                confirmButtonText.value = "Allow from settings"
                action = {
                    Toast.makeText(
                        context,
                        "Please Enable " + currentPermission.permissionType.type + " permission for " + appName,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts(PACKAGE, context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            } else {
                action = { permissionState.launchPermissionRequest() }
            }
        }
    }


    CbDecisionDialog(
        onConfirmClick = {
            showPermissionAlert.value = false
            action()
        },
        onDismissClick = {
            showPermissionAlert.value = false
        },
        title = permissionHandler.getPermissionPopUpTitle(),
        text = permissionHandler.getPermissionPopUpText(),
        confirmText = confirmButtonText.value,
    )
}


@Composable
fun Footer(
    context: Activity,
    onclickSkip: () -> Unit,
    skipColor: Color,
    currentPermission: CbPermission
) {
    val density = LocalDensity.current

    val showSkipAlert = remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxWidth(),
        visible = currentPermission.canBeSkipped,
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
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            showSkipAlert.value = true
                        },
                    text = "Skip >>",

                    style = TextStyle(
                        color = skipColor,
                        textDecoration = TextDecoration.Underline,
                    ),
                )
            }
        }
    }
    if (showSkipAlert.value) {
        CbDecisionDialog(
            onConfirmClick = {
                PermissionHandlerFactory.getHandlerForPermission(currentPermission.permissionType)!!
                    .skipPermission(context)
                showSkipAlert.value = false
                onclickSkip()
            },
            onDismissClick = {
                showSkipAlert.value = false
            },
            title = "Skip Optional permission",
            text = "Some feature might not work as expected",
            confirmText = "Skip anyway",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionButton(
    context: Activity,
    appName: String,
    modifier: Modifier,
    cardColor: Color,
    cardTextColor: Color,
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
            Crossfade(targetState = currentPermission,
                animationSpec = tween(animationDuration)) {
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
                        colors = ListItemDefaults.colors(containerColor = cardColor),
                        headlineContent = {
                            WelcomeInfoText(
                                text = PermissionHandlerFactory.getHandlerForPermission(
                                    it.permissionType
                                )
                                !!.getPermissionButtonText(),
                                color = cardTextColor,
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
                                tint = cardTextColor
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
        )
    }
}