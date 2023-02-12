package com.cb.cbtools.data.model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "exception_record",
    indices = [Index(value = ["exception_details"])],
)
data class ExceptionRecord(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "exception_details")
    var exceptionDetails: String?,

    @ColumnInfo(name = "class_name")
    var className: String?,

    @ColumnInfo(name = "line_number")
    var lineNumber: Int?,

    @ColumnInfo(name = "time")
    var time: Calendar,

    @ColumnInfo(name = "stack_trace")
    var stackTrace: String?

) {
    constructor(exceptionDetails: String?, className: String?, lineNumber: Int?) : this(
        id = 0,
        exceptionDetails = exceptionDetails,
        className = className,
        lineNumber = lineNumber,
        time = Calendar.getInstance(),
        stackTrace = null
    )

    constructor(
        exception: Throwable?,
        className: String?,
        lineNumber: Int?
    ) : this(
        id = 0,
        exceptionDetails = exception?.localizedMessage,
        className = className,
        lineNumber = lineNumber,
        time = Calendar.getInstance(),
        stackTrace = Log.getStackTraceString(exception)
    )


}

