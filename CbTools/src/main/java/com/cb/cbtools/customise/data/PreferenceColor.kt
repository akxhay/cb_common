package com.cb.cbtools.customise.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PreferenceColor(
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("value") @Expose var value: String? = null
)