package com.cb.cbtools.customise.data

import com.cb.cbtools.customise.constants.PreferenceType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Preference(
    @SerializedName("type") @Expose var type: PreferenceType = PreferenceType.DEFAULT,
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("summary") @Expose var summary: String? = null,
    @SerializedName("action") @Expose var action: Array<String>? = null,
    @SerializedName("icon") @Expose var icon: PreferenceIcon? = null,
    @SerializedName("pref") @Expose var pref: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Preference

        if (type != other.type) return false
        if (title != other.title) return false
        if (summary != other.summary) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (summary?.hashCode() ?: 0)
        result = 31 * result + (icon?.hashCode() ?: 0)
        return result
    }
}