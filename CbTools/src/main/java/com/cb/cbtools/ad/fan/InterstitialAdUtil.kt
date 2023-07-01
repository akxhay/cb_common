package com.cb.cbtools.ad.fan

import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener

class InterstitialAdUtil {
    companion object {
        private val TAG = InterstitialAdUtil::class.java.simpleName
        val map = HashMap<String, InterstitialAd>()

        private val interstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                Log.e(TAG, "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                Log.e(TAG, "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad, adError: AdError) {
                Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")

            }

            override fun onAdClicked(ad: Ad) {
                Log.d(TAG, "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.d(TAG, "Interstitial ad impression logged!")
            }
        }

        fun init(context: Context?, placementId: String) {
            Log.e(TAG, "initialize")
            if (!map.contains(placementId)) {
                map[placementId] = InterstitialAd(context, placementId)
            }

            if (!map[placementId]!!.isAdLoaded) {
                map[placementId]!!.loadAd(
                    map[placementId]!!.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build()
                )
            }
        }

        fun show(context: Context?, placementId: String) {
            if (!map.contains(placementId) || map[placementId]!!.isAdInvalidated) {
                init(context, placementId)

            } else if (map[placementId]!!.isAdLoaded) {
                map[placementId]!!.show()
            }
        }
    }
}