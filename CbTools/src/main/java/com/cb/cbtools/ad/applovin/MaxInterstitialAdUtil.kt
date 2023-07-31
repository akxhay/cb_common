package com.cb.cbtools.ad.applovin

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import java.util.concurrent.TimeUnit
import kotlin.math.pow

class MaxInterstitialAdUtil {
    companion object {

        private val TAG = MaxInterstitialAdUtil::class.java.simpleName

        private var retryAttempt = 0.0
        private lateinit var interstitialAd: MaxInterstitialAd

        private val interstitialAdListener = object : MaxAdListener {

            override fun onAdLoaded(p0: MaxAd?) {
                Log.d(TAG, "Interstitial ad Loaded!")
                retryAttempt = 0.0
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                Log.d(TAG, "Interstitial ad Displayed!")
            }

            override fun onAdHidden(p0: MaxAd?) {
                Log.d(TAG, "Interstitial ad Hidden!")
                interstitialAd.loadAd()
            }

            override fun onAdClicked(p0: MaxAd?) {
                Log.d(TAG, "Interstitial ad Clicked!")
            }

            override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
                Log.e(
                    TAG,
                    "Interstitial ad failed to load: " + p1?.message
                )
                retryAttempt++
                val delayMillis =
                    TimeUnit.SECONDS.toMillis(2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong())

                Handler(Looper.getMainLooper()).postDelayed({
                    interstitialAd.loadAd()
                }, delayMillis)
            }

            override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
                Log.d(TAG, "Interstitial ad DisplayFailed!")
                interstitialAd.loadAd()

            }
        }


        fun createInterstitialAd(placementId: String, activity: Activity) {
            interstitialAd = MaxInterstitialAd(placementId, activity)
            interstitialAd.setListener(interstitialAdListener)
            interstitialAd.loadAd()
        }

        fun showAd() {
            if (interstitialAd.isReady) {
                interstitialAd.showAd()
            }
        }
    }

}