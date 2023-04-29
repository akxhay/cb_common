package com.cb.cbtools.dynamic

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.cb.cbtools.dynamic.data.DynamicApp
import com.cb.cbtools.dynamic.data.PreferenceCategory
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


    fun getAppName(): String {
        return dynamicApp.appName
    }

    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(dynamicApp.sharedPrefName, Context.MODE_PRIVATE)
    }


    fun getPreferenceCategories(): List<PreferenceCategory>? {
        return dynamicApp.preferences
    }
}