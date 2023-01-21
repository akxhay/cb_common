package com.cb.cbtools.composables

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.dynamic.constants.ActionType
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CbListItem(
    title: String,
    summary: String?,
    primaryBitmap: ImageBitmap?,
    primaryImageVector: ImageVector?,
    secondaryDrawable: Drawable? = null,
    primaryIconSize: Dp = 30.dp,
    dynamicConfig: DynamicConfig,
    actionType: ActionType = ActionType.DEFAULT,
    checked: Boolean = false,
    onChange: (Boolean) -> Unit,
    onClick: () -> Unit = {},
    actionImageVector: @Composable () -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = ListItemDefaults.colors(containerColor = dynamicConfig.getBackgroundColor()),
        headlineText = {
            Column(modifier = Modifier) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = dynamicConfig.getPrimaryTextOnBackGroundColor(),
                    overflow = TextOverflow.Ellipsis
                )

                summary?.let { it ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = dynamicConfig.getSecondaryTextOnBackgroundColor(),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        leadingContent = ({
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (primaryImageVector != null) {
                        Icon(
                            modifier = Modifier.size(primaryIconSize),
                            imageVector = primaryImageVector,
                            contentDescription = "dp",
                            tint = dynamicConfig.getPrimaryTextOnBackGroundColor()
                        )
                    } else {
                        Image(
                            painter = BitmapPainter(image = primaryBitmap!!),
                            contentDescription = "dp",
                            contentScale = ContentScale.Crop,
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
                            contentDescription = "dp",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }

        }),
        trailingContent = ({
            if (actionType != ActionType.DEFAULT) {
                Action(
                    actionType,
                    checked,
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
    checked: Boolean,
    onChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        val checkedState = remember {
            mutableStateOf(checked)
        }
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

