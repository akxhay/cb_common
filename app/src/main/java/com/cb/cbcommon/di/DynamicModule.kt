package com.cb.cbcommon.di

import android.content.Context
import com.cb.cbcommon.DynamicConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DynamicModule {
    @Provides
    fun providesDynamicConfig(
        @ApplicationContext context: Context,
    ): DynamicConfig =
        DynamicConfig(context)

}