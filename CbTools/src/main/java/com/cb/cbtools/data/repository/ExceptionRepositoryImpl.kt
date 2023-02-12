package com.cb.cbtools.data.repository

import com.cb.cbtools.data.dao.ExceptionDao
import com.cb.cbtools.data.model.ExceptionRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExceptionRepositoryImpl @Inject constructor(private val dao: ExceptionDao) :
    ExceptionRepository {

    override suspend fun insertExceptionRecords(exceptionRecord: ExceptionRecord?) =
        dao.insertExceptionRecords(exceptionRecord)


    override fun getException(): Flow<List<ExceptionRecord>> =
        dao.getException()

    override suspend fun deleteRecords() {
        dao.deleteRecords()

    }
}