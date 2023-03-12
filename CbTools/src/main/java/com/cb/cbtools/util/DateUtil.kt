package com.cb.cbtools.util

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {


    private val appValidFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val completeDateTimeFormatWoSec: DateFormat =
        SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH)
    private val completeDateTimeFormat: DateFormat =
        SimpleDateFormat("yyyy:MM:dd hh:mm:ss aa", Locale.ENGLISH)

    private val dateFormatterMMMM: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    private val timeFormatter24: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    private var timeFormatter12 = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)

    fun isAppValid(debugMode: Boolean, validTillDate: String): Boolean {
        Log.d("DateUtil", "debug mode : $debugMode")
        return !debugMode || (appValidFormat.parse(validTillDate)?.compareTo(Date())
            ?: 1) == 1
    }

    fun convertDateMMMM(calendar: Calendar): String {
        if (isToday(calendar)) return "Today"
        return dateFormatterMMMM.format(calendar.time)
    }

    fun convertTime12(calendar: Calendar): String {
        return timeFormatter12.format(calendar.time)
    }

    fun convertTime24(calendar: Calendar): String {
        return timeFormatter24.format(calendar.time)

    }

    fun convertCompleteDateTimeFormatWoSec(calendar: Calendar): String {
        return completeDateTimeFormatWoSec.format(calendar.time)
    }

    fun convertCompleteDateTime(calendar: Calendar): String {
        return completeDateTimeFormat.format(calendar.time)
    }

    fun getNextRepeatDate(calendar: Calendar): Calendar {
        calendar.add(Calendar.DATE, 1)
        return calendar
    }

    fun isFutureDate(calendar: Calendar): Boolean {
        return Calendar.getInstance().before(calendar)
    }

    private fun isToday(calendar: Calendar?): Boolean {
        return areDatesSame(calendar, Calendar.getInstance())
    }

    fun areDatesSame(calender1: Calendar?, calender2: Calendar?): Boolean {
        return calender1 != null && calender2 != null && calender1[Calendar.DATE] == calender2[Calendar.DATE]
    }

}