package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicStyle(
    @SerializedName("backgroundColor") @Expose var backgroundColor: DynamicColor = DynamicColor(
        "hex",
        "#FFFFFF"
    ),
    @SerializedName("appbarTitleColor") @Expose var appbarTitleColor: DynamicColor = DynamicColor(
        "default",
        "White"
    ),
    @SerializedName("appbarBackgroundColor") @Expose var appbarBackgroundColor: DynamicColor = DynamicColor(
        "colorScheme",
        "primary"
    ),
    @SerializedName("dividerColor") @Expose var dividerColor: DynamicColor = DynamicColor(
        "default",
        "Transparent"
    ),
    @SerializedName("headerColor") @Expose var headerColor: DynamicColor = DynamicColor(
        "hex",
        "#004D40"
    ),
    @SerializedName("titleColor") @Expose var titleColor: DynamicColor = DynamicColor(
        "default",
        "Black"
    ),
    @SerializedName("summaryColor") @Expose var summaryColor: DynamicColor = DynamicColor(
        "default",
        "Gray"
    ),
    @SerializedName("iconColor") @Expose var iconColor: DynamicColor = DynamicColor(
        "hex",
        "#004D40"
    ),

    )