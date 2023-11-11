package com.cb.cbtools.service


import android.util.Log
import com.cb.cbtools.data.model.ExceptionRecord
import com.cb.cbtools.data.repository.ExceptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExceptionHelper(
    private val exceptionRepository: ExceptionRepository
) {
    private val tag = "ExceptionHelper"

    fun saveException(exceptionRecord: ExceptionRecord) {
        Log.d(tag, exceptionRecord.toString())
        CoroutineScope(Dispatchers.IO).launch {
            exceptionRepository.insertExceptionRecords(exceptionRecord)
        }
    }

    fun saveException(paramThread: Thread, paramThrowable: Throwable) {
        saveException(
            ExceptionRecord(
                exception = paramThrowable,
                className = paramThread.javaClass.name,
                lineNumber = paramThread.stackTrace.getOrNull(2)?.lineNumber ?: -1
            )
        )
    }
}