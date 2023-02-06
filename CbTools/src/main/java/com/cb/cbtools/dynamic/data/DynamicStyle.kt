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
    @SerializedName("iconTintOnBackgroundColor") @Expose var iconTintOnBackgroundColor: DynamicColor? = null,

    @SerializedName("cardBackgroundColor") @Expose var cardBackgroundColor: DynamicColor? = null,
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

    @SerializedName("inputTextBackgroundColor") @Expose var inputTextBackgroundColor: DynamicColor? = null,
    @SerializedName("inputTextContentColor") @Expose var inputTextContentColor: DynamicColor? = null,
    @SerializedName("inputTextPlaceholderColor") @Expose var inputTextPlaceholderColor: DynamicColor? = null,
    @SerializedName("inputTextCursorColor") @Expose var inputTextCursorColor: DynamicColor? = null,
    @SerializedName("inputTextFocusedColor") @Expose var inputTextFocusedColor: DynamicColor? = null,
    @SerializedName("inputTextUnFocusedColor") @Expose var inputTextUnFocusedColor: DynamicColor? = null,
    @SerializedName("inputTextErrorColor") @Expose var inputTextErrorColor: DynamicColor? = null,
    @SerializedName("inputErrorContainerColor") @Expose var inputErrorContainerColor: DynamicColor? = null,
    @SerializedName("inputTextOnErrorContainerColor") @Expose var inputTextOnErrorContainerColor: DynamicColor? = null,


    @SerializedName("sentBubbleColor") @Expose var sentBubbleColor: DynamicColor? = null,
    @SerializedName("receivedBubbleColor") @Expose var receivedBubbleColor: DynamicColor? = null,
    @SerializedName("primaryTextOnSentBubbleColor") @Expose var primaryTextOnSentBubbleColor: DynamicColor? = null,
    @SerializedName("primaryTextOnReceivedBubbleColor") @Expose var primaryTextOnReceivedBubbleColor: DynamicColor? = null,
    @SerializedName("secondaryTextOnSentBubbleColor") @Expose var secondaryTextOnSentBubbleColor: DynamicColor? = null,
    @SerializedName("secondaryTextOnReceivedBubbleColor") @Expose var secondaryTextOnReceivedBubbleColor: DynamicColor? = null,
    @SerializedName("dateBubbleColor") @Expose var dateBubbleColor: DynamicColor? = null,
    @SerializedName("primaryTextOnDateBubbleColor") @Expose var primaryTextOnDateBubbleColor: DynamicColor? = null,
    @SerializedName("chatScreenInputBackgroundColor") @Expose var chatScreenInputBackgroundColor: DynamicColor? = null,
    @SerializedName("chatScreenInputContentColor") @Expose var chatScreenInputContentColor: DynamicColor? = null,


    @SerializedName("checkedIconColor") @Expose var checkedIconColor: DynamicColor? = null,
    @SerializedName("unCheckedIconColor") @Expose var unCheckedIconColor: DynamicColor? = null,

    @SerializedName("urlTextColor") @Expose var urlTextColor: DynamicColor? = null,

    )

