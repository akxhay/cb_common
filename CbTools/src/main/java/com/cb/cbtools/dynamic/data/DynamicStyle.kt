package com.cb.cbtools.dynamic.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DynamicStyle(

    @SerializedName("welcomeScreenBackgroundColor") @Expose var welcomeScreenBackgroundColor: DynamicColor? = null,
    @SerializedName("welcomeScreenTitleColor") @Expose var welcomeScreenTitleColor: DynamicColor? = null,
    @SerializedName("welcomeScreenSummaryColor") @Expose var welcomeScreenSummaryColor: DynamicColor? = null,
    @SerializedName("welcomeScreenCardBackgroundColor") @Expose var welcomeScreenCardBackgroundColor: DynamicColor? = null,
    @SerializedName("welcomeScreenCardContentColor") @Expose var welcomeScreenCardContentColor: DynamicColor? = null,
    @SerializedName("welcomeScreenSkipTextColor") @Expose var welcomeScreenSkipTextColor: DynamicColor? = null,


    @SerializedName("appbarBackgroundColor") @Expose var appbarBackgroundColor: DynamicColor? = null,
    @SerializedName("appbarTitleColor") @Expose var appbarTitleColor: DynamicColor? = null,
    @SerializedName("appBarMenuIconColor") @Expose var appBarMenuIconColor: DynamicColor? = null,
    @SerializedName("appBarMenuTextColor") @Expose var appBarMenuTextColor: DynamicColor? = null,

    @SerializedName("floatingBackgroundColor") @Expose var floatingBackgroundColor: DynamicColor? = null,
    @SerializedName("floatingContentColor") @Expose var floatingContentColor: DynamicColor? = null,


    @SerializedName("backgroundColor") @Expose var backgroundColor: DynamicColor? = null,
    @SerializedName("primaryTextOnBackgroundColor") @Expose var primaryTextOnBackgroundColor: DynamicColor? = null,
    @SerializedName("secondaryTextOnBackgroundColor") @Expose var secondaryTextOnBackgroundColor: DynamicColor? = null,

    @SerializedName("cardColor") @Expose var cardColor: DynamicColor? = null,
    @SerializedName("cardBorderColor") @Expose var cardBorderColor: DynamicColor? = null,
    @SerializedName("cardShadowColor") @Expose var cardShadowColor: DynamicColor? = null,
    @SerializedName("primaryTextOnCardColor") @Expose var primaryTextOnCardColor: DynamicColor? = null,
    @SerializedName("secondaryTextOnCardColor") @Expose var secondaryTextOnCardColor: DynamicColor? = null,
    @SerializedName("iconOnCardColor") @Expose var iconOnCardColor: DynamicColor? = null,


    @SerializedName("radioCheckedColor") @Expose var radioCheckedColor: DynamicColor? = null,
    @SerializedName("radioUnCheckedColor") @Expose var radioUnCheckedColor: DynamicColor? = null,


    @SerializedName("dividerColor") @Expose var dividerColor: DynamicColor? = null,


    @SerializedName("alertBackgroundColor") @Expose var alertBackgroundColor: DynamicColor? = null,
    @SerializedName("alertContentColor") @Expose var alertContentColor: DynamicColor? = null,
    @SerializedName("alertTitleColor") @Expose var alertTitleColor: DynamicColor? = null,
    @SerializedName("alertConfirmButtonColor") @Expose var alertConfirmButtonColor: DynamicColor? = null,
    @SerializedName("alertConfirmTextColor") @Expose var alertConfirmTextColor: DynamicColor? = null,
    @SerializedName("alertDismissTextColor") @Expose var alertDismissTextColor: DynamicColor? = null,
    @SerializedName("alertDividerColor") @Expose var alertDividerColor: DynamicColor? = null,

    )
