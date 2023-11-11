package com.cb.cbcommon

import com.cb.cbcommon.notification.NotificationReceiver
import com.cb.cbcommon.presentation.MainActivity
import com.cb.cbtools.constants.Constants
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.dto.CbPermission

object AppConstants {
    val requiredPermissionOnStartup = listOf(
        CbPermission(
            permissionType = PermissionType.PERMISSION_ACCESS_NOTIFICATION,
            data = mapOf(
                Constants.KEY_NOTIFICATION_RECEIVER to NotificationReceiver::class.qualifiedName!!
            ),
            canBeSkipped = false
        ),
        CbPermission(
            permissionType = PermissionType.PERMISSION_POST_NOTIFICATION,
            canBeSkipped = false
        ),
        CbPermission(
            permissionType = PermissionType.PERMISSION_BATTERY_OPTIMIZE,
            canBeSkipped = true
        )
    )


    val version: String by lazy {
        "Version :  ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    val home: Class<*> = MainActivity::class.java
}
