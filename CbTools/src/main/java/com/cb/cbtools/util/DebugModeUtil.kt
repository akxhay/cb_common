package com.cb.cbtools.util

import android.widget.Toast
import androidx.activity.ComponentActivity
import com.cb.cbtools.util.DateUtil.isAppValid
import com.cb.cbtools.BuildConfig

object DebugModeUtil {
    fun isAppDebug(): Boolean = BuildConfig.DEBUG

    fun checkValidity(activity: ComponentActivity, validTillDate: String): Boolean {
        if (isAppDebug() && !isAppValid(validTillDate)) {
            Toast.makeText(
                activity,
                "This app is no longer valid! Please contact Developer ‍🤠",
                Toast.LENGTH_LONG
            ).show()
            activity.finishAffinity()
            activity.finish()
            return false
        }
        return true
    }
}