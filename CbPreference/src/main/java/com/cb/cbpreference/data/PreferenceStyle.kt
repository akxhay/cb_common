package com.cb.cbpreference.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PreferenceStyle(
    @SerializedName("backgroundColor") @Expose var backgroundColor: PreferenceColor? = null,
    @SerializedName("appbarTitleColor") @Expose var appbarTitleColor: PreferenceColor? = null,
    @SerializedName("appbarBackgroundColor") @Expose var appbarBackgroundColor: PreferenceColor? = null,
    @SerializedName("dividerColor") @Expose var dividerColor: PreferenceColor? = null,
    @SerializedName("headerColor") @Expose var headerColor: PreferenceColor? = null,
    @SerializedName("titleColor") @Expose var titleColor: PreferenceColor? = null,
    @SerializedName("summaryColor") @Expose var summaryColor: PreferenceColor? = null,
    @SerializedName("iconColor") @Expose var iconColor: PreferenceColor? = null

)
