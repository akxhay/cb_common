package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicIcon(
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("value") @Expose var value: String? = null,
    @SerializedName("imageVector") @Expose var imageVector: String? = null,
)