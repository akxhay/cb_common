package com.cb.cbpreference.util

import android.content.Context
import android.util.Log
import com.cb.cbpreference.data.PreferenceScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getPreferenceScreen(context: Context): PreferenceScreen {

    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("preferences.json")
            .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
        Log.e("ioException", ioException.toString())
    }
    val listCountryType = object : TypeToken<PreferenceScreen>() {}.type
    return Gson().fromJson(jsonString, listCountryType)
}