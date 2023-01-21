package com.cb.cbtools.preference

import android.content.Context

object SharedPreference {
    private const val CB_SHARED_PREF = "CB_SHARED_PREF"

    fun getBooleanPref(
        prefName: String?,
        default: Boolean = false,
        context: Context
    ): Boolean {
        return context.getSharedPreferences(CB_SHARED_PREF, Context.MODE_PRIVATE)
            .getBoolean(prefName, default)
    }

    fun setBooleanPref(prefName: String?, value: Boolean, context: Context) {
        context.getSharedPreferences(CB_SHARED_PREF, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(prefName, value).apply()
    }

    fun toggleBooleanPref(prefName: String?, context: Context) {
        context.getSharedPreferences(CB_SHARED_PREF, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(prefName, getBooleanPref(prefName, context = context)).apply()
    }
}