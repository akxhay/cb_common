package com.cb.cbtools.ad.applovin

import android.content.Context
import android.util.Log
import com.applovin.sdk.AppLovinSdk


class AppLovinHelper {

    companion object {
        const val TAG = "AppLovinHelper"

        fun initialize(context: Context?, debugMode: Boolean = false) {
            if (!AppLovinSdk.getInstance(context).isInitialized) {
                AppLovinSdk.getInstance(context).mediationProvider = "max"

                AppLovinSdk.initializeSdk(context) {
                    Log.d(TAG, it.toString())

                }

            }
        }
    }
}