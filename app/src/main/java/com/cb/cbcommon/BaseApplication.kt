package com.cb.cbcommon

import android.app.Application
import com.cb.cbtools.data.model.ExceptionRecord
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.service.ExceptionHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var dynamicConfig: DynamicConfig

    @Inject
    lateinit var exceptionHelper: ExceptionHelper

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
            exceptionHelper.saveException(
                ExceptionRecord(
                    exception = paramThrowable,
                    className = paramThread.javaClass.name,
                    lineNumber = paramThread.stackTrace[2].lineNumber
                )
            )
        }
    }

    companion object {
        private lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication {
            return baseApplication
        }
    }
}