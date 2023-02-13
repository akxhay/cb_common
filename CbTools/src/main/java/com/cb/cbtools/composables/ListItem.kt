package com.cb.cbtools.composables

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.constants.ActionType
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CbListItem(
    modifier: Modifier = Modifier,
    iconUnit: (@Composable () -> Unit)? = null,
    titleUnit: (@Composable () -> Unit)? = null,
    title: String? = null,
    titleColor: Color? = null,
    summary: String? = null,
    summaryUnit: (@Composable () -> Unit)? = null,
    primaryByteArray: ByteArray? = null,
    primaryBitmap: ImageBitmap? = null,
    primaryImageVector: ImageVector? = null,
    primaryDrawable: Drawable? = null,
    secondaryDrawable: Drawable? = null,
    secondaryImageVector: ImageVector? = null,
    primaryIconSize: Dp = 30.dp,
    secondaryIconSize: Dp = 20.dp,

    iconClick: (() -> Unit)? = null,
    dynamicConfig: DynamicConfig,
    actionType: ActionType = ActionType.DEFAULT,
    checked: MutableState<Boolean>? = null,
    onChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    actionImageVector: @Composable () -> Unit = {},
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    maxSummaryLines: Int = 1
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        colors = ListItemDefaults.colors(containerColor = dynamicConfig.getBackgroundColor()),
        headlineText = {
            if (titleUnit != null)
                titleUnit()
            else
                Column(modifier = Modifier) {
                    title?.let { it ->
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleMedium,
                            color = titleColor ?: dynamicConfig.getPrimaryTextOnBackGroundColor(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }


                    if (summaryUnit != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        summaryUnit()
                    } else
                        summary?.let { it ->
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = dynamicConfig.getSecondaryTextOnBackgroundColor(),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = maxSummaryLines,
                            )
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
                                tint = dynamicConfig.getIconTintOnBackgroundColor()
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
                                tint = dynamicConfig.getIconTintOnBackgroundColor()
                            )
                        }
                    }
                }
            }

        }),
        trailingContent = ({
            if (actionType != ActionType.DEFAULT) {
                Action(
                    actionType,
                    checked!!,
                    onChange
                )
            } else {
                actionImageVector()
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

