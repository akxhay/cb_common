package com.cb.cbpro

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.cb.cbtools.ad.fan.InterstitialAdUtil
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.util.DateUtil
import java.util.Date

class ProUtil {
    companion object {
        private const val PRO_DATE = "proDate"
        private const val PRO_MODE = "proMode"

        private val TAG = ProUtil::class.java.simpleName

        fun isNotPro(
            context: Context,
            dynamicConfig: DynamicConfig,
            interstitialId: String
        ): Boolean {
            val isNotPro = !isPro(dynamicConfig)
            if (isNotPro) {
                InterstitialAdUtil.init(context, interstitialId)
            }
            return isNotPro
        }

        fun isPro(dynamicConfig: DynamicConfig): Boolean {
            Log.i(TAG, "checking isPro")
            var isPro = true
            val proDate = dynamicConfig.getSharedPreferences()
                .getString(PRO_DATE, null)
            Log.i(TAG, "proDate $proDate")

            if (proDate != null) {
                isPro = DateUtil.isProModeValid(proDate)
            }
            Log.i(TAG, "isPro $isPro")
            val enabled = dynamicConfig.getSharedPreferences()
                .getString(PRO_MODE, null) == "enabled"
            Log.i(TAG, "enabled $enabled")

            return enabled
        }

        fun setProMode(proMode: String, dynamicConfig: DynamicConfig) {
            Log.i(TAG, "inside setProMode $proMode")
            if (proMode == "enabled") {
                val date = DateUtil.getAppValidFormat(Date())
                Log.i(TAG, "date $date")
                dynamicConfig.getSharedPreferences().edit()
                    .putString(
                        PRO_DATE,
                        date
                    )
                    .apply()
            }
            dynamicConfig.getSharedPreferences().edit()
                .putString(PRO_MODE, proMode).apply()
        }

        fun checkProMode(context: Context, dynamicConfig: DynamicConfig) {
            Log.i(TAG, "inside checkProMode")
            val proDate = dynamicConfig.getSharedPreferences()
                .getString(PRO_DATE, null)

            val proMode = dynamicConfig.getSharedPreferences()
                .getString(PRO_MODE, null)
            Log.i(TAG, "proDate $proDate")
            if (proMode == "enabled" && proDate != null && !DateUtil.isProModeValid(proDate)) {
                setProMode("disabled", dynamicConfig)
                Toast.makeText(context, "Pro mode disabled", Toast.LENGTH_SHORT).show()

            }
        }
    }
}