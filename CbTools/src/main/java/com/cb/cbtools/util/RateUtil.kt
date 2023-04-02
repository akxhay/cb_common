package com.cb.cbtools.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log

object RateUtil {

    fun rate(
        activity: Activity,
    ) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=" + activity.applicationContext.packageName)
            activity.startActivity(intent)

        } catch (e: Exception) {
            Log.e("RateUtil", "exception: " + e.localizedMessage)
        }
    }

}