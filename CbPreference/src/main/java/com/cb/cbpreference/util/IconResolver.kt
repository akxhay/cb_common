package com.cb.cbpreference.util

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
        return when (icon.type) {
            "base64" -> parseBase64(icon.value)
            "svg" -> parseSvg(icon.value)

            else -> {
                LocalContext.current.getDrawable(com.google.android.material.R.drawable.tooltip_frame_dark)!!
                    .toBitmap().asImageBitmap()
            }
        }
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