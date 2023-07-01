package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Expression(
    @SerializedName("key") @Expose var key: String? = null,
    @SerializedName("value") @Expose var value: String? = null,
    @SerializedName("operator") @Expose var operator: String = "eq",

    )