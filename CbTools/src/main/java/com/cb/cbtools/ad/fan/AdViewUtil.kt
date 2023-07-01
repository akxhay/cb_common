package com.cb.cbtools.ad.fan

import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdView

class AdViewUtil {
    companion object {
        private val TAG = AdViewUtil::class.java.simpleName
        val map = HashMap<String, AdView>()
        private val adListener: AdListener = object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                Log.e(TAG, "ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                Log.d(TAG, "Ad loaded!")
            }

            override fun onAdClicked(ad: Ad) {
                Log.d(TAG, "Ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.d(TAG, "Ad impression logged!")
            }
        }

        fun getAdView(
            label: String,
            context: Context,
            placementId: String,
            adSize: FanAdSize
        ): AdView {
            if (!map.contains(label)) {
                map[label] = AdView(context, placementId, adSize.adSize).apply {
                    loadAd(this.buildLoadAdConfig().withAdListener(adListener).build())
                }
            }
            return map[label]!!
        }

    }
}