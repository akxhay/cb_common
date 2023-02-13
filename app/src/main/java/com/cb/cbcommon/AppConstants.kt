package com.cb.cbcommon

import com.cb.cbcommon.notification.NotificationReceiver
import com.cb.cbcommon.presentation.MainActivity
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.constants.Constants
import com.cb.cbtools.permission.constants.PermissionType

object AppConstants {
    val requiredPermissionOnStartup = arrayListOf(
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
    const val appNameRes: Int = R.string.app_name
    const val appDescRes: Int = R.string.app_desc
    const val appIconRes: Int = R.drawable.play_store_512

    val home = MainActivity::class.java
}