package com.cb.cbtools.presentation.common

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cb.cbtools.dynamic.DynamicConfig
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CbAppBar(
    title: String,
    icon: ImageVector? = null,
    byteArray: ByteArray? = null,
    drawable: Drawable? = null,
    backAction: (() -> Unit)? = null,
    dynamicConfig: DynamicConfig,
    primaryIconSize: Dp = 40.dp,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .background(dynamicConfig.getAppBarBackGroundColor())
            )
            {
                if (byteArray != null || icon != null || drawable != null) {
                    Box(
                        modifier = Modifier.padding(end = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (byteArray != null) {
                            val bitmap = BitmapFactory.decodeByteArray(
                                byteArray,
                                0,
                                byteArray.size
                            ).asImageBitmap()
                            Image(
                                painter = BitmapPainter(image = bitmap),
                                contentDescription = "dp",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(primaryIconSize)
                            )
                        } else if (icon != null) {
                            Icon(
                                modifier = Modifier
                                    .size(primaryIconSize)
                                    .padding(bottom = 3.dp, end = 3.dp),
                                imageVector = icon,
                                contentDescription = "icon",
                                tint = dynamicConfig.getAppBarMenuIconColor()
                            )
                        } else if (drawable != null) {
                            Image(
                                modifier = Modifier
                                    .size(primaryIconSize)
                                    .padding(bottom = 3.dp, end = 3.dp),

                                painter = rememberDrawablePainter(
                                    drawable = drawable
                                ),
                                contentDescription = "icon",
                            )

                        }
                    }
                }
                Text(
                    title,
                    color = dynamicConfig.getAppBarTitleColor(),
                    modifier = Modifier.padding(top = 3.dp), maxLines = 1
                )
            }
        },
        navigationIcon = {
            backAction?.let { it ->
                IconButton(onClick = { it() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "ArrowBack",
                        tint = dynamicConfig.getAppBarMenuIconColor()
                    )
                }
            }
        },
        colors =
        TopAppBarDefaults.topAppBarColors(containerColor = dynamicConfig.getAppBarBackGroundColor()),
        actions = actions
    )
}
