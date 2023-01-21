package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicApp(
    @SerializedName("appName") @Expose var appName: String = "Application",
    @SerializedName("sharedPrefName") @Expose var sharedPrefName: String = "CB_SETTINGS",
    @SerializedName("dynamicStyle") @Expose var style: DynamicStyle = DynamicStyle(),
    @SerializedName("preferenceCategories") @Expose var preferences: List<PreferenceCategory>? = null
)