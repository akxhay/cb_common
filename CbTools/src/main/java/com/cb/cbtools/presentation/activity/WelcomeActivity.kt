package com.cb.cbtools.presentation.activity

import com.cb.cbtools.permission.constants.Constants.batteryOptimization
import com.cb.cbtools.permission.constants.Constants.notificationAccess
import com.cb.cbtools.permission.constants.Constants.postNotification

private val requiredPermissionOnStartup =
    arrayListOf(
        notificationAccess, postNotification, batteryOptimization
    )


