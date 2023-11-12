package com.cb.cbcommon.presentation.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cb.cbcommon.R
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemActionCheckBox
import com.cb.cbtools.presentation.common.CbListItemActionCustom
import com.cb.cbtools.presentation.common.CbListItemActionSwitch
import com.cb.cbtools.presentation.common.CbListItemIconByteArrayPrimary
import com.cb.cbtools.presentation.common.CbListItemIconDouble
import com.cb.cbtools.presentation.common.CbListItemIconDrawablePrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageBitmapPrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorPrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorSecondary
import com.cb.cbtools.presentation.common.CbListItemSummary
import com.cb.cbtools.presentation.common.CbListItemTitle
import java.io.ByteArrayOutputStream


@ExperimentalAnimationApi
@Composable
fun ListItemsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            CbAppBar(
                title = "CB list items",
                backAction = { navController.navigateUp() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(state = scrollState)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            CbListItem(
                titleUnit = { CbListItemTitle(text = "Simple list item") },
            )

            CbListItem(
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Summary") }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Image vector") }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Disabled") },
                enabled = false
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconDrawablePrimary(drawable = context.getDrawable(R.drawable.ic_bot)) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Drawable") }
            )
            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_bot
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageBitmapPrimary(
                        bitmap = bitmap.asImageBitmap()
                    ) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Image bitmap") }
            )

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            CbListItem(
                iconUnit = {
                    CbListItemIconByteArrayPrimary(
                        byteArray = stream.toByteArray()
                    ) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "Image byte array") }
            )

            CbListItem(
                iconUnit = {
                    CbListItemIconDouble(primaryIconUnit = {
                        CbListItemIconDrawablePrimary(drawable = context.getDrawable(R.drawable.ic_bot)) {
                        }
                    }, secondaryIconUnit = {
                        CbListItemIconImageVectorSecondary(imageVector = Icons.Default.AccountCircle)
                    }
                    )
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "double icon") }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                        Toast.makeText(context, "Icon clicked", Toast.LENGTH_SHORT).show()
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item") },
                summaryUnit = { CbListItemSummary(text = "icon and item click and long press") },
                onClick = {
                    Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
                },
                onLongPress = {
                    Toast.makeText(context, "Item long pressed", Toast.LENGTH_SHORT).show()

                }
            )
            val checked = rememberSaveable { mutableStateOf(false) }
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item " + if (checked.value) "Checked" else "Unchecked") },
                summaryUnit = { CbListItemSummary(text = "Checkbox") },
                actionUnit = {
                    CbListItemActionCheckBox(state = checked.value, onChange = {
                        checked.value = !checked.value
                    })
                }
            )
            val switch = rememberSaveable { mutableStateOf(true) }
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                        Toast.makeText(context, "Icon clicked", Toast.LENGTH_SHORT).show()
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item " + if (switch.value) "Switched on" else "Switched off") },
                summaryUnit = { CbListItemSummary(text = "Switch") },
                onClick = {
                    switch.value = !switch.value
                },
                actionUnit = {
                    CbListItemActionSwitch(state = switch.value, onChange = {
                        switch.value = !switch.value
                    })
                }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item ") },
                summaryUnit = { CbListItemSummary(text = "With custom action") },

                actionUnit = {
                    CbListItemActionCustom {
                        CbListItemIconImageVectorPrimary(imageVector = Icons.Default.Delete, iconTint =  MaterialTheme.colorScheme.error) {
                            Toast.makeText(context, "item will be deleted", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            )
            CbListItem(
                iconUnit = {
                    CbListItemIconImageVectorPrimary(imageVector = Icons.Default.AccountCircle) {
                        Toast.makeText(context, "Icon clicked", Toast.LENGTH_SHORT).show()
                    }
                },
                titleUnit = { CbListItemTitle(text = "List item " + if (switch.value) "Switched on" else "Switched off") },
                summaryUnit = { CbListItemSummary(text = "Disabled") },
                onClick = {
                    switch.value = !switch.value
                },
                actionUnit = {
                    CbListItemActionSwitch(state = switch.value, onChange = {
                        switch.value = !switch.value
                    })
                },
                enabled = false
            )
        }
    }
}

