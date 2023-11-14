package com.cb.cbcommon

import android.app.Application
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.service.AppInfoService
import com.cb.cbtools.service.ExceptionHelper
import com.cb.cbtools.util.PlayStoreUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var dynamicConfig: DynamicConfig

    @Inject
    lateinit var exceptionHelper: ExceptionHelper

    @Inject
    lateinit var appInfoService: AppInfoService

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        PlayStoreUtil.printAdId(this)
        Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
            exceptionHelper.saveException(paramThread, paramThrowable)
        }
    }

    companion object {
        private lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication = baseApplication
    }
}
