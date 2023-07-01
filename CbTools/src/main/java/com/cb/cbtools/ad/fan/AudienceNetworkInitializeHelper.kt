package com.cb.cbtools.ad.fan

import android.content.Context
import android.util.Log
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.AudienceNetworkAds.InitListener
import com.facebook.ads.AudienceNetworkAds.InitResult

class AudienceNetworkInitializeHelper : InitListener {
    override fun onInitialized(result: InitResult) {
        Log.d(AudienceNetworkAds.TAG, result.message)
    }

    companion object {
        fun initialize(context: Context?, debugMode: Boolean = false) {
            if (!AudienceNetworkAds.isInitialized(context)) {
                if (debugMode) {
                    AdSettings.turnOnSDKDebugger(context)
                }
                AudienceNetworkAds
                    .buildInitSettings(context)
                    .withInitListener(AudienceNetworkInitializeHelper())
                    .initialize()
            }
        }
    }
}