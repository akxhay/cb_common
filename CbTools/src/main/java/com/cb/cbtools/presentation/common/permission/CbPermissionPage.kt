package com.cb.cbtools.presentation.common.permission

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.cbtools.R
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.factory.PermissionHandlerFactory
import com.cb.cbtools.presentation.composable.animationDuration

@Composable
fun CbPermissionPage(
    @DrawableRes appIcon: Int,
    appName: String,
    appDesc: String,
    version: String,
    onclickSkip: () -> Unit,
    onPermissionClick: () -> Unit,
    currentPermission: CbPermission,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    tertiaryTextColor: Color = MaterialTheme.colorScheme.primary,
    cardColor: Color = MaterialTheme.colorScheme.primary,
    cardTextColor: Color = MaterialTheme.colorScheme.onPrimary
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
            modifier = Modifier
                .weight(10f),
            currentPermission = currentPermission,
            cardColor = cardColor,
            cardTextColor = cardTextColor,
            onPermissionClick = onPermissionClick
        )
        Footer(
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


@Composable
fun Footer(
    onclickSkip: () -> Unit,
    skipColor: Color,
    currentPermission: CbPermission
) {
    val density = LocalDensity.current

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
                            onclickSkip()
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
}


@Composable
fun PermissionButton(
    modifier: Modifier,
    cardColor: Color,
    cardTextColor: Color,
    currentPermission: CbPermission,
    onPermissionClick: () -> Unit
) {


    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier,
            Alignment.Center
        ) {
            Crossfade(
                targetState = currentPermission,
                animationSpec = tween(animationDuration),
                label = currentPermission.permissionType.toString()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                        .clickable {
                            onPermissionClick()
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
}

@Composable
@Preview
fun PreviewCbPermissionPage() {
    val requiredPermissionOnStartup = listOf(
        CbPermission(
            permissionType = PermissionType.PERMISSION_POST_NOTIFICATION,
            canBeSkipped = true
        ),
        CbPermission(
            permissionType = PermissionType.PERMISSION_BATTERY_OPTIMIZE,
            canBeSkipped = true
        )
    )
    
    CbPermissionPage(
        appName = "CB tools ",
        appDesc = "This is app description",
        appIcon = R.drawable.ic_round_cropped,
        version = "1.0.0",
        currentPermission = requiredPermissionOnStartup.first(),
        onclickSkip = {
        },
        onPermissionClick = {

        }
    )
}