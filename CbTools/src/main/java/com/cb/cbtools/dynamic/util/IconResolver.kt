package com.cb.cbtools.dynamic.util

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.PictureDrawable
import android.util.Base64
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stars
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.caverock.androidsvg.SVG
import com.cb.cbtools.dynamic.data.DynamicIcon

object IconResolver {

    @Composable
    fun getBitmap(icon: DynamicIcon): ImageBitmap {
        val context = LocalContext.current
        return when (icon.type) {
            "base64" -> parseBase64(icon.value)
            "svg" -> parseSvg(icon.value)
            "drawable" -> parseDrawable(context, icon.value)
            else -> {
                ContextCompat.getDrawable(
                    context,
                    com.google.android.material.R.drawable.tooltip_frame_dark
                )!!.toBitmap(width = 120, height = 120)
                    .asImageBitmap()
            }
        }
    }


    private fun parseDrawable(context: Context, value: String?): ImageBitmap {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier(
            value, "drawable",
            context.packageName
        )
        return ContextCompat.getDrawable(context, resourceId)!!.toBitmap(width = 120, height = 120)
            .asImageBitmap()
    }

    private fun parseBase64(base64: String?): ImageBitmap {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
    }

    private fun parseSvg(svgString: String?): ImageBitmap {
        val svg = SVG.getFromString(svgString)
        return PictureDrawable(svg.renderToPicture()).toBitmap().asImageBitmap()
    }

    fun getImageVector(icon: DynamicIcon?, defaultIcon: ImageVector): ImageVector {
        if (icon?.imageVector != null && icon.value != null) {
            return when (icon.imageVector) {
                "default" -> getDefaultIcon(icon.value!!, defaultIcon)
                "filled" -> getFilledIcon(icon.value!!, defaultIcon)

                else -> defaultIcon
            }

        }
        return defaultIcon
    }

    private fun getFilledIcon(value: String?, defaultIcon: ImageVector): ImageVector {
        return when (value) {
            "money" -> Icons.Filled.MonetizationOn
            "notification" -> Icons.Filled.Notifications
            "battery" -> Icons.Filled.BatteryAlert
            "notification2" -> Icons.Filled.NotificationsActive
            "question" -> Icons.Filled.QuestionMark
            "share" -> Icons.Filled.Share
            "stars" -> Icons.Filled.Stars

            else -> defaultIcon
        }

    }

    private fun getDefaultIcon(value: String?, defaultIcon: ImageVector): ImageVector {
        return when (value) {
            "money" -> Icons.Default.MonetizationOn
            "notification" -> Icons.Default.Notifications
            "battery" -> Icons.Default.BatteryAlert
            "notification2" -> Icons.Default.NotificationsActive
            "question" -> Icons.Default.QuestionMark
            "share" -> Icons.Default.Share
            "stars" -> Icons.Default.Stars

            else -> defaultIcon
        }
    }


}