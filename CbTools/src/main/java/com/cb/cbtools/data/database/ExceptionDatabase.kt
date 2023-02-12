package com.cb.cbtools.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cb.cbtools.data.converter.DateConverter
import com.cb.cbtools.data.dao.ExceptionDao
import com.cb.cbtools.data.model.ExceptionRecord

@Database(
    entities = [ExceptionRecord::class],
    version = 1
)
@TypeConverters(DateConverter::class)

abstract class ExceptionDatabase : RoomDatabase() {
    abstract fun exceptionDao(): ExceptionDao

    companion object {
        const val DATABASE_NAME = "exception_database"
    }
}