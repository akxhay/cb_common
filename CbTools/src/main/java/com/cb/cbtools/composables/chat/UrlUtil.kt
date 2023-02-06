package com.cb.cb_test.application.util.common

import android.webkit.URLUtil
import java.util.regex.Matcher
import java.util.regex.Pattern

object UrlUtil {
    fun getStartAndEndOfUrls(text: String): List<Array<Int>> {
        val result = ArrayList<Array<Int>>()
        var i = 0;
        var j: Int;

        for (split in text.split(" ")) {
            j = i + split.length
            if (URLUtil.isValidUrl(split)) {
                result.add(arrayOf(i, j))
            }
            i = j + 1
        }
        return result
    }


}

fun extractUrls(text: String): Set<String> {
    val containedUrls: MutableSet<String> = HashSet()
    val urlRegex =
        "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
    val pattern: Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher: Matcher = pattern.matcher(text)
    while (urlMatcher.find()) {
        containedUrls.add(
            text.substring(
                urlMatcher.start(0),
                urlMatcher.end(0)
            )
        )
    }
    return containedUrls
}