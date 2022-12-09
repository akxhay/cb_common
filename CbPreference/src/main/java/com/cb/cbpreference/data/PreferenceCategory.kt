package com.cb.cbpreference.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PreferenceScreen(
    @SerializedName("preferenceStyle") @Expose var style: PreferenceStyle? = null,
    @SerializedName("preferenceCategories") @Expose var preferences: List<PreferenceCategory>? = null,
)


data class PreferenceCategory(
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("preferences") @Expose var preferences: List<Preference>? = null,
)


data class Preference(
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("summary") @Expose var summary: String? = null,
    @SerializedName("icon") @Expose var icon: String? = null,
)

data class PreferenceStyle(
    @SerializedName("appName") @Expose var appName: String? = null,
)