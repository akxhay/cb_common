package com.cb.cbcommon.data.di

import android.content.Context
import com.cb.cbtools.data.repository.ExceptionRepository
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.service.ExceptionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CBModule {
    @Provides
    fun providesExceptionHelper(
        exceptionRepository: ExceptionRepository
    ): ExceptionHelper =
        ExceptionHelper(exceptionRepository)


    @Provides
    fun providesDynamicConfig(
        @ApplicationContext context: Context,
    ): DynamicConfig =
        DynamicConfig(context)
}