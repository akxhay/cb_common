package com.cb.cbtools.ad.fan

import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdSize
import com.facebook.ads.AdView

class AdViewUtil {
    companion object {
        private val TAG = AdViewUtil::class.java.simpleName

        fun getAdView(
            label: String,
            context: Context,
            placementId: String,
            adSize: AdSize
        ): AdView {
            val adListener: AdListener = object : AdListener {
                override fun onError(ad: Ad, adError: AdError) {
                    Log.e(TAG, "$label::ad failed to load: " + adError.errorMessage)
                }

                override fun onAdLoaded(ad: Ad) {
                    Log.d(TAG, "$label::Ad loaded!")
                }

                override fun onAdClicked(ad: Ad) {
                    Log.d(TAG, "$label::Ad clicked!")
                }

                override fun onLoggingImpression(ad: Ad) {
                    Log.d(TAG, "$label::Ad impression logged!")
                }
            }
            return AdView(context, placementId, adSize).apply {
                loadAd(this.buildLoadAdConfig().withAdListener(adListener).build())
            }
        }

    }
}