package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicColor(
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("value") @Expose var value: String? = null
)