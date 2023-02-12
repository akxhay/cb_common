package com.cb.cbtools.data.repository

import com.cb.cbtools.data.model.ExceptionRecord
import kotlinx.coroutines.flow.Flow

interface ExceptionRepository {

    suspend fun insertExceptionRecords(exceptionRecord: ExceptionRecord?)
    fun getException(): Flow<List<ExceptionRecord>>
    suspend fun deleteRecords()
}