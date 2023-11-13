package com.cb.cbtools.data.converter

import androidx.room.TypeConverter
import java.util.Calendar


object DateConverter {
    @TypeConverter
    fun toCalendar(l: Long?): Calendar? {
        val c = Calendar.getInstance()
        l?.let { c.timeInMillis = it }
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.time?.time
    }
}