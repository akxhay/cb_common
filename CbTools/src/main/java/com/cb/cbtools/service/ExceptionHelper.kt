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
    val TAG = "ExceptionHelper"

    fun saveException(exceptionRecord: ExceptionRecord) {
        Log.d(TAG, exceptionRecord.toString())
        CoroutineScope(Dispatchers.IO).launch {
            exceptionRepository.insertExceptionRecords(exceptionRecord)
        }
    }

}