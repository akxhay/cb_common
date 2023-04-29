package com.cb.cbtools.presentation.common

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.constants.ActionType
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun CbListItem(
    modifier: Modifier = Modifier,
    iconUnit: (@Composable () -> Unit)? = null,
    titleUnit: (@Composable () -> Unit),
    summaryUnit: (@Composable () -> Unit)? = null,
    primaryByteArray: ByteArray? = null,
    primaryBitmap: ImageBitmap? = null,
    primaryImageVector: ImageVector? = null,
    primaryDrawable: Drawable? = null,
    secondaryDrawable: Drawable? = null,
    secondaryImageVector: ImageVector? = null,
    primaryIconSize: Dp = 30.dp,
    secondaryIconSize: Dp = 20.dp,
    primaryIconTint: Color = MaterialTheme.colorScheme.primary,
    secondaryIconTint: Color = MaterialTheme.colorScheme.primary,
    iconClick: (() -> Unit)? = null,
    actionType: ActionType = ActionType.DEFAULT,
    checked: MutableState<Boolean>? = null,
    onChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    actionImageVector: @Composable () -> Unit = {},
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background
) {
    val context = LocalContext.current
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (enabled)
                    onClick()
                else
                    Toast
                        .makeText(context, "This field is disabled", Toast.LENGTH_SHORT)
                        .show()
            },
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
            if (iconUnit != null ||
                primaryImageVector != null ||
                primaryBitmap != null ||
                primaryDrawable != null ||
                secondaryDrawable != null ||
                primaryByteArray != null
            ) {
                Box(
                    modifier = Modifier.clickable { if (iconClick != null) iconClick() else onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    if (iconUnit != null) {
                        iconUnit()
                    }
                    Box(
                        modifier = Modifier.size(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (primaryImageVector != null) {
                            Icon(
                                modifier = Modifier.size(primaryIconSize),
                                imageVector = primaryImageVector,
                                contentDescription = "dp",
                                tint = primaryIconTint
                            )
                        } else if (primaryByteArray != null) {
                            Image(
                                painter = BitmapPainter(
                                    image = BitmapFactory.decodeByteArray(
                                        primaryByteArray,
                                        0,
                                        primaryByteArray.size
                                    ).asImageBitmap()
                                ),
                                contentDescription = "dp",
                                contentScale = ContentScale.Crop,
                                modifier =
                                Modifier
                                    .size(primaryIconSize)
                                    .clip(CircleShape)
                                    .border(0.dp, Color.Transparent, CircleShape)
                            )
                        } else if (primaryBitmap != null) {
                            Image(
                                painter = BitmapPainter(primaryBitmap),
                                contentDescription = "dp",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(primaryIconSize)
                            )
                        } else if (primaryDrawable != null) {
                            Image(
                                painter = rememberDrawablePainter(
                                    drawable = primaryDrawable
                                ),
                                contentScale = ContentScale.Fit,
                                contentDescription = "dp",
                                modifier = Modifier.size(primaryIconSize)
                            )
                        }
                    }
                    secondaryDrawable?.let {
                        Box(
                            modifier = Modifier.size(50.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                painter = rememberDrawablePainter(
                                    drawable = it
                                ),
                                contentScale = ContentScale.Fit,
                                contentDescription = "secondaryDrawable",
                                modifier = Modifier.size(secondaryIconSize)
                            )
                        }
                    }

                    secondaryImageVector?.let {
                        Box(
                            modifier = Modifier.size(50.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Icon(
                                modifier = Modifier.size(secondaryIconSize),
                                imageVector = it,
                                contentDescription = "secondaryImageVector",
                                tint = secondaryIconTint
                            )
                        }
                    }
                }
            }

        }),
        trailingContent = ({
            if (enabled) {
                if (actionType != ActionType.DEFAULT) {
                    Action(
                        actionType,
                        checked!!,
                        onChange
                    )
                } else {
                    actionImageVector()
                }
            }
        })
    )
}


@Composable
fun Action(
    actionType: ActionType,
    checkedState: MutableState<Boolean>,
    onChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {

        if (actionType == ActionType.SWITCH) {

            Switch(
                modifier = Modifier.padding(end = 20.dp),
                checked = checkedState.value,
                onCheckedChange = { changedValue ->
                    checkedState.value = changedValue
                    onChange(changedValue)
                }
            )
        } else if (actionType == ActionType.CHECKBOX) {
            Checkbox(
                modifier = Modifier.padding(end = 20.dp),
                checked = checkedState.value,
                onCheckedChange = { changedValue ->
                    checkedState.value = changedValue
                    onChange(changedValue)
                }
            )
        }

    }
}

