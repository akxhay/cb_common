package com.cb.cbtools.di

import android.content.Context
import android.content.pm.PackageManager
import com.cb.cbtools.service.AppInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppInfoModule {

    @Provides
    fun providesAppInfoService(
        @ApplicationContext context: Context,
        packageManager: PackageManager
    ): AppInfoService =
        AppInfoService(context, packageManager)

}