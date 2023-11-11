package com.cb.cbtools.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cb.cbtools.data.model.ExceptionRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ExceptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExceptionRecords(exceptionRecord: ExceptionRecord)

    @Query("SELECT * FROM exception_record ORDER BY time DESC")
    fun getException(): Flow<List<ExceptionRecord>>

    @Query("DELETE FROM exception_record")
    suspend fun deleteRecords()
}