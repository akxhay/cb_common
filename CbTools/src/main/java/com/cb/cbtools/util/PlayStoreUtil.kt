package com.cb.cbtools.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import java.util.concurrent.Executors

object PlayStoreUtil {
    private val TAG = PlayStoreUtil::class.java.simpleName

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
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            activity.startActivity(intent)

        } catch (e: Exception) {
            Log.e("RateUtil", "exception: " + e.localizedMessage)
        }
    }

    fun printAdId(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val adInfo =
                    AdvertisingIdClient.getAdvertisingIdInfo(context)
                val myId = adInfo.id
                Log.i(TAG, "UID_ANDROID $myId")
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

}