package com.cb.cbtools.permission.factory

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

class PostNotification : PermissionHandler {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    override fun getPermissionType() = PermissionType.PERMISSION_POST_NOTIFICATION

    override fun getPermissionPopUpTitle() = "Provide permissions for sending notification"

    override fun getPermissionPopUpText() =
        "Foreground notification permission is required from Android 13 and above.\nPlease grant the permission."

    override fun getPermissionButtonText() = "Allow us to notify you"

    override fun getPermissionIcon() = Icons.Default.NotificationsActive

    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            true
        } else {
            super.isPermitted(context, data)
        }
    }

}