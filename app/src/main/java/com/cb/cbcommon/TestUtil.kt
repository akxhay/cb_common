package com.cb.cbcommon

import android.content.Context
import android.util.Log

class TestUtil {

    fun test(context: Context, name: String) {
        Log.i(
            "InterstitialAdUtil",
            "Test success ${context.javaClass.canonicalName} with name $name"
        )
    }

    companion object {
        fun test(context: Context, name: String) {
            Log.i("InterstitialAdUtil", "Test 2 success ${context.javaClass.canonicalName} with name $name")
        }

    }
}