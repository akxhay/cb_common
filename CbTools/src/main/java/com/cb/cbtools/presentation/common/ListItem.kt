package com.cb.cbtools.presentation.common

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.constants.ActionType
import com.cb.cbtools.presentation.theme.getGoogleFontFamily
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CbListItem(
    modifier: Modifier = Modifier,
    iconUnit: (@Composable () -> Unit)? = null,
    titleUnit: (@Composable () -> Unit),
    summaryUnit: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    actionUnit: @Composable () -> Unit = {},
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background
) {
    val context = LocalContext.current
    val disabledAction = {
        Toast
            .makeText(context, "This field is disabled", Toast.LENGTH_SHORT)
            .show()
    }
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    if (enabled)
                        onClick()
                    else
                        disabledAction()
                },
                onLongClick = {
                    if (enabled)
                        onLongPress()
                    else
                        disabledAction()
                },
            ),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        colors = ListItemDefaults.colors(containerColor = containerColor),
        headlineContent = {
            Column(modifier = Modifier) {
                titleUnit()
                Spacer(modifier = Modifier.height(2.dp))
                if (summaryUnit != null) {
                    summaryUnit()
                }
            }

        },
        leadingContent = ({
            if (iconUnit != null) {
                iconUnit()
            }
        }),
        trailingContent = ({
            if (enabled) {
                actionUnit()
            }
        })
    )
}


@Composable
fun CbListItemAction(
    actionType: ActionType,
    state: Boolean = false,
    onChange: () -> Unit,
) {
    if (actionType == ActionType.CHECKBOX) {
        CbListItemActionCheckBox(
            state = state,
            onChange = onChange
        )
    } else if (actionType == ActionType.SWITCH) {
        CbListItemActionSwitch(
            state = state,
            onChange = onChange
        )
    }
}

@Composable
fun CbListItemTitle(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = getGoogleFontFamily(
            name = "Poppins",
            weights = listOf(
                FontWeight.Medium
            )
        ),
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )

}

@Composable
fun CbListItemSummary(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = getGoogleFontFamily(
            name = "Roboto",
            weights = listOf(
                FontWeight.Light
            )
        ),
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )

}


@Composable
fun CbListItemPrimaryIconBox(
    iconClick: () -> Unit,
    iconUnit: (@Composable () -> Unit),
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable { iconClick() },
        contentAlignment = Alignment.Center
    ) {
        iconUnit()
    }
}

@Composable
fun CbListItemSecondaryIconBox(
    iconUnit: (@Composable () -> Unit),
) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        iconUnit()
    }
}

@Composable
fun CbListItemActionBox(
    iconUnit: (@Composable () -> Unit),
) {
    Box(
        modifier = Modifier
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        iconUnit()
    }
}

@Composable
fun CbListItemActionCheckBox(
    state: Boolean = false,
    onChange: () -> Unit
) {
    CbListItemActionBox {
        Checkbox(
            modifier = Modifier.padding(end = 20.dp),
            checked = state,
            onCheckedChange = {
                onChange()
            }
        )
    }
}

@Composable
fun CbListItemActionSwitch(
    state: Boolean = false,
    onChange: () -> Unit
) {
    CbListItemActionBox {
        Switch(
            modifier = Modifier.padding(end = 20.dp),
            checked = state,
            onCheckedChange = {
                onChange()
            }
        )
    }
}

@Composable
fun CbListItemActionCustom(
    actionUnit: (@Composable () -> Unit),
) {
    CbListItemActionBox {
        actionUnit()
    }
}

@Composable
fun CbListItemIconImageVectorSecondary(
    imageVector: ImageVector,
    iconSize: Dp = 20.dp,
    iconTint: Color = MaterialTheme.colorScheme.primary,
) {
    CbListItemSecondaryIconBox {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = imageVector,
            contentDescription = "dp",
            tint = iconTint
        )
    }
}

@Composable
fun CbListItemIconDrawableSecondary(
    drawable: Drawable? = null,
    iconSize: Dp = 30.dp,
) {
    val painter = rememberDrawablePainter(
        drawable = drawable
    )
    CbListItemSecondaryIconBox {
        Image(
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = "dp",
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun CbListItemActionIconBox(
    iconClick: () -> Unit,
    iconUnit: (@Composable () -> Unit),
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clickable { iconClick() },
        contentAlignment = Alignment.BottomEnd
    ) {
        iconUnit()
    }
}

@Composable
fun CbListItemIconImageVectorSecondary(
    imageVector: ImageVector,
    iconSize: Dp = 30.dp,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    iconClick: () -> Unit,
) {
    CbListItemActionIconBox(iconClick) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = imageVector,
            contentDescription = "dp",
            tint = iconTint
        )
    }
}

@Composable
fun CbListItemIconImageVectorPrimary(
    imageVector: ImageVector,
    iconSize: Dp = 50.dp,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    iconClick: () -> Unit,
) {
    CbListItemPrimaryIconBox(iconClick = iconClick) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = imageVector,
            contentDescription = "dp",
            tint = iconTint
        )
    }
}


@Composable
fun CbListItemIconByteArrayPrimary(
    byteArray: ByteArray,
    iconSize: Dp = 50.dp,
    iconClick: () -> Unit,
) {
    CbListItemPrimaryIconBox(iconClick = iconClick) {
        Image(
            painter = BitmapPainter(
                image = BitmapFactory.decodeByteArray(
                    byteArray,
                    0,
                    byteArray.size
                ).asImageBitmap()
            ),
            contentDescription = "ByteArray",
            contentScale = ContentScale.Crop,
            modifier =
            Modifier
                .size(iconSize)
                .clip(CircleShape)
                .border(0.dp, Color.Transparent, CircleShape)
        )
    }

}


@Composable
fun CbListItemIconImageBitmapPrimary(
    bitmap: ImageBitmap,
    iconSize: Dp = 50.dp,
    iconClick: () -> Unit,
) {
    CbListItemPrimaryIconBox(iconClick = iconClick) {
        Image(
            painter = BitmapPainter(bitmap),
            contentDescription = "dp",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(iconSize)
        )
    }

}


@Composable
fun CbListItemIconDrawablePrimary(
    drawable: Drawable? = null,
    iconSize: Dp = 50.dp,
    iconClick: () -> Unit
) {
    val painter = rememberDrawablePainter(
        drawable = drawable
    )
    CbListItemPrimaryIconBox(iconClick = iconClick) {
        Image(
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = "dp",
            modifier = Modifier.size(iconSize)
        )
    }
}


@Composable
fun CbListItemIconDouble(
    primaryIconUnit: (@Composable () -> Unit),
    secondaryIconUnit: (@Composable () -> Unit),

    ) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        primaryIconUnit()
        secondaryIconUnit()
    }
}

@Preview
@Composable
fun PreviewCbListItem() {
    CbListItem(
        actionUnit = {
            CbListItemActionCustom {
                CbListItemIconImageVectorSecondary(
                    imageVector = Icons.Default.Delete,
                    iconTint = MaterialTheme.colorScheme.error
                ) {

                }
            }
        },
        titleUnit = { CbListItemTitle(text = "List item") },
        summaryUnit = { CbListItemSummary(text = "double icon") }
    )

}