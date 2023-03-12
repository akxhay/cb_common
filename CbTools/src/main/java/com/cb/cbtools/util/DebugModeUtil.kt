package com.cb.cbtools.util

import android.widget.Toast
import androidx.core.app.ComponentActivity
import com.cb.cbtools.util.DateUtil.isAppValid

object DebugModeUtil {

    fun checkValidity(
        activity: ComponentActivity,
        validTillDate: String,
        debugMode: Boolean
    ): Boolean {
        if (debugMode && !isAppValid(validTillDate)) {
            closeApplication(activity)
            return false
        }
        return true
    }

    private fun closeApplication(activity: ComponentActivity) {
        Toast.makeText(
            activity,
            "This app is no longer valid! Please contact Developer ‍🤠",
            Toast.LENGTH_LONG
        ).show()
        activity.finishAffinity()
        activity.finish()
    }
}