package com.cb.cbpreference.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PreferenceScreen(
    @SerializedName("appName") @Expose var appName: String = "Application",
    @SerializedName("sharedPrefName") @Expose var sharedPrefName: String = "CB_SETTINGS",
    @SerializedName("preferenceStyle") @Expose var style: PreferenceStyle? = null,
    @SerializedName("preferenceCategories") @Expose var preferences: List<PreferenceCategory>? = null,
    @SerializedName("observables") @Expose var observables: Map<String, Any>? = null,

    )