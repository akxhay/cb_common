package com.cb.cbpro.application.util.common

object StringUtils {
    fun getTrimmedString(string: String, length: Int): String {
        if (string.length > length)
            return string.substring(0, length)
        return string
    }
}