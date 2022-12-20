package com.cb.cbpreference.util

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.PictureDrawable
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import com.caverock.androidsvg.SVG
import com.cb.cbpreference.data.PreferenceIcon

object IconResolver {

    @Composable
    fun getBitmap(icon: PreferenceIcon): ImageBitmap {
        val context = LocalContext.current
        return when (icon.type) {
            "base64" -> parseBase64(icon.value)
            "svg" -> parseSvg(icon.value)
            "drawable" -> parseDrawable(context, icon.value)
            else -> {
                context.getDrawable(com.google.android.material.R.drawable.tooltip_frame_dark)!!
                    .toBitmap().asImageBitmap()
            }
        }
    }

    private fun parseDrawable(context: Context, value: String?): ImageBitmap {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier(
            value, "drawable",
            context.packageName
        )
        if (resourceId != null) {
            return resources.getDrawable(resourceId).toBitmap(width = 120, height = 120)
                .asImageBitmap()
        }
        return context.getDrawable(com.google.android.material.R.drawable.tooltip_frame_dark)!!
            .toBitmap().asImageBitmap()

    }

    private fun parseBase64(base64: String?): ImageBitmap {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
    }

    private fun parseSvg(svgString: String?): ImageBitmap {
        val svg = SVG.getFromString(svgString)
        return PictureDrawable(svg.renderToPicture()).toBitmap().asImageBitmap()
    }


}