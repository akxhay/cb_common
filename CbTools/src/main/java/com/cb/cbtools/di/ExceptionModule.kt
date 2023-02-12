package com.cb.cbtools.di

import android.content.Context
import androidx.room.Room
import com.cb.cbtools.data.dao.ExceptionDao
import com.cb.cbtools.data.database.ExceptionDatabase
import com.cb.cbtools.data.database.ExceptionDatabase.Companion.DATABASE_NAME
import com.cb.cbtools.data.repository.ExceptionRepository
import com.cb.cbtools.data.repository.ExceptionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExceptionModule {


    @Provides
    fun providesDao(exceptionDatabase: ExceptionDatabase): ExceptionDao =
        exceptionDatabase.exceptionDao()

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ExceptionDatabase =
        Room.databaseBuilder(context, ExceptionDatabase::class.java, DATABASE_NAME)
            .build()


    @Provides
    fun providesRepository(exceptionDao: ExceptionDao): ExceptionRepository =
        ExceptionRepositoryImpl(exceptionDao)

}