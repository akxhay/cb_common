package com.cb.cbtools.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val completeDateFormat: DateFormat =
        SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH)
    private val appValidFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val targetFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    private val ruleDateTimeFormatter: DateFormat =
        SimpleDateFormat("yyyy:MM:dd hh:mm aa", Locale.ENGLISH)

    //    val ruleDateFormatter: DateFormat = SimpleDateFormat("yyyy:MM:dd", Locale.ENGLISH)
    private val ruleDateFormatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    private val ruleTimeFormatter: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    private var targetTimeFormat = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)


    fun isAppValid(validTillDate: String): Boolean {
        return (appValidFormat.parse(validTillDate)?.compareTo(Date()) ?: 1) == 1
    }


    fun convertDateForChatScreen(calendar: Calendar): String {
        if (isToday(calendar)) return "Today"
        return targetFormat.format(calendar.time)
    }

    private fun isToday(calendar: Calendar?): Boolean {
        return areDatesSame(calendar, Calendar.getInstance())
    }

    fun areDatesSame(calender1: Calendar?, calender2: Calendar?): Boolean {
        return calender1 != null && calender2 != null && calender1[Calendar.DATE] == calender2[Calendar.DATE]
    }


    fun convertChatDate(calendar: Calendar): String {
        return targetTimeFormat.format(calendar.time)
    }

    fun convertLogDate(calendar: Calendar): String {
        return completeDateFormat.format(calendar.time)
    }

    fun convertRuleDate(calendar: Calendar): String {
        return ruleDateFormatter.format(calendar.time)
    }

    fun convertRuleTime(calendar: Calendar): String {
        return ruleTimeFormatter.format(calendar.time)

    }

    fun convertRuleDateTime(calendar: Calendar): String {
        return ruleDateTimeFormatter.format(calendar.time)
    }

    fun getNextRepeatDate(calendar: Calendar): Calendar {
        calendar.add(Calendar.DATE, 1)
        return calendar
    }


    fun isFutureDate(calendar: Calendar): Boolean {
        return Calendar.getInstance().before(calendar)
    }


}