package com.cb.cbtools.dynamic.util

import android.content.Context
import android.util.Log
import com.cb.cbtools.dynamic.data.DynamicApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun buildDynamicApp(context: Context): DynamicApp {

    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("dynamic_app.json")
            .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
        Log.e("ioException", ioException.toString())
    }
    val listCountryType = object : TypeToken<DynamicApp>() {}.type
    return Gson().fromJson(jsonString, listCountryType)
}