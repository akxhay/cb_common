package com.cb.cbtools.presentation.common

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CbAppBar(
    title: String,
    icon: ImageVector? = null,
    byteArray: ByteArray? = null,
    drawable: Drawable? = null,
    backAction: (() -> Unit)? = null,
    primaryIconSize: Dp = 40.dp,
    actions: @Composable RowScope.() -> Unit = {},
    appbarBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    appbarTitleColor: Color = MaterialTheme.colorScheme.onPrimary,
    appBarMenuIconColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .background(appbarBackgroundColor)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (byteArray != null || icon != null || drawable != null) {
                    Box(
                        modifier = Modifier.padding(end = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            byteArray != null -> {
                                val bitmap = BitmapFactory.decodeByteArray(
                                    byteArray,
                                    0,
                                    byteArray.size
                                ).asImageBitmap()
                                Image(
                                    modifier = Modifier.size(primaryIconSize),
                                    painter = BitmapPainter(image = bitmap),
                                    contentDescription = "Icon Image",
                                    contentScale = ContentScale.Crop
                                )
                            }

                            icon != null -> {
                                Icon(
                                    modifier = Modifier.size(primaryIconSize),
                                    imageVector = icon,
                                    contentDescription = "Icon",
                                    tint = appBarMenuIconColor
                                )
                            }

                            drawable != null -> {
                                Image(
                                    modifier = Modifier.size(primaryIconSize),
                                    painter = rememberDrawablePainter(drawable = drawable),
                                    contentDescription = "Icon"
                                )
                            }
                        }
                    }
                }
                Text(
                    text = title,
                    color = appbarTitleColor,
                    modifier = Modifier.padding(start = 8.dp),
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        navigationIcon = {
            backAction?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = appBarMenuIconColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = appbarBackgroundColor),
        actions = actions
    )
}

