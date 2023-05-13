package com.cb.cbtools.dto

import android.graphics.drawable.Drawable
import java.io.Serializable
import java.util.*

data class AppListInfo(
    var pkg: String? = null,
    var name: String? = null,
    var icon: Drawable? = null,
    var appType: Int = 0,
    var isEnabled: Boolean = false
) : Comparable<AppListInfo>, Serializable {
    fun changeEnabled() {
        isEnabled = !isEnabled
    }

    override fun compareTo(other: AppListInfo): Int {
        return name!!.compareTo(other.name!!)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AppListInfo
        return pkg == that.pkg
    }

    override fun hashCode(): Int {
        return Objects.hash(pkg)
    }

    override fun toString(): String {
        return "AppListInfo{" +
                "pkg='" + pkg + '\'' +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", appType=" + appType +
                ", isEnabled=" + isEnabled +
                '}'
    }
}