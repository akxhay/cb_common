package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PreferenceCategory(
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("preferences") @Expose var preferences: List<Preference>? = null,
    @SerializedName("showExpression") @Expose var showExpression: Expression? = null,
)
