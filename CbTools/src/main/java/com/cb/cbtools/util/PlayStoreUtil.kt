package com.cb.cbtools.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log

object PlayStoreUtil {

    fun rate(
        activity: Activity,
    ) {
        openPlayStore(activity = activity, packageName = activity.applicationContext.packageName)
    }

    fun openPlayStore(
        activity: Activity,
        packageName: String
    ) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)
            activity.startActivity(intent)

        } catch (e: Exception) {
            Log.e("RateUtil", "exception: " + e.localizedMessage)
        }
    }

}