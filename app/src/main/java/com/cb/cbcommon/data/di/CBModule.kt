package com.cb.cbcommon.data.di

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import com.cb.cbcommon.data.constant.AppConstants.CB_SETTINGS
import com.cb.cbcommon.util.common.NotificationWriteHelper
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


    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(CB_SETTINGS, Context.MODE_PRIVATE)

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    @Provides
    fun providesNotificationWriteHelper(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): NotificationWriteHelper =
        NotificationWriteHelper(context, notificationManager)
}