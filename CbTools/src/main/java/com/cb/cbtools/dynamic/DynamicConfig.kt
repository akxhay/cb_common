package com.cb.cbcommon

import android.content.Context
import android.util.Log
import com.cb.cbtools.dynamic.data.DynamicApp
import com.cb.cbtools.dynamic.data.DynamicStyle
import com.cb.cbtools.dynamic.util.buildDynamicApp

class DynamicConfig(private val context: Context) {
    private lateinit var dynamicApp: DynamicApp

    init {
        Log.d("DynamicConfig", "init ")
        updateConfig()
    }

    private fun updateConfig() {
        dynamicApp = buildDynamicApp(context = context)
        Log.d("DynamicConfig", "dynamicApp : $dynamicApp")
    }

    fun getDynamicApp(): DynamicApp {
        return dynamicApp
    }

    fun getDynamicAppStyle(): DynamicStyle {
        return dynamicApp.style
    }
}