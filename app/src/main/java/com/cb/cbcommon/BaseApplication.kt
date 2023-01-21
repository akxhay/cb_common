package com.cb.cbcommon

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var dynamicConfig: DynamicConfig

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        private lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication {
            return baseApplication
        }
    }
}