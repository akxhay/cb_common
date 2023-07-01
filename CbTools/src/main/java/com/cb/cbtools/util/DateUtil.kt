package com.cb.cbtools.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {


    private val appValidFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val completeDateTimeFormatWoSec: DateFormat =
        SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH)
    private val completeDateTimeFormat: DateFormat =
        SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa", Locale.ENGLISH)

    private val dateFormatterMMMM: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    private val timeFormatter24: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    private var timeFormatter12 = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)

    private val dateTimeFormat: DateFormat =
        SimpleDateFormat("dd/MM/yy hh:mm:ss aa", Locale.ENGLISH)

    fun isAppValid(validTillDate: String): Boolean {
        return (appValidFormat.parse(validTillDate)?.compareTo(Date()) ?: 1) == 1
    }

    fun isProModeValid(validTillDate: String): Boolean {
        try {
            val date1 = Calendar.getInstance()
            date1.set(Calendar.HOUR_OF_DAY, 0)
            date1.set(Calendar.MINUTE, 0)
            date1.set(Calendar.SECOND, 0)
            date1.set(Calendar.MILLISECOND, 0)

            val date2 = Calendar.getInstance()
            date2.timeInMillis = appValidFormat.parse(validTillDate)!!.time
            date2.set(Calendar.HOUR_OF_DAY, 0)
            date2.set(Calendar.MINUTE, 0)
            date2.set(Calendar.SECOND, 0)
            date2.set(Calendar.MILLISECOND, 0)

            return date2 >= date1
        } catch (e: Exception) {
            return true
        }
    }

    fun getAppValidFormat(date: Date): String {
        return appValidFormat.format(date)
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

    fun convertDateTime(calendar: Calendar): String {
        return dateTimeFormat.format(calendar.time)
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