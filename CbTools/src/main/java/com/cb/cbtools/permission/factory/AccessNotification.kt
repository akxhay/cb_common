package com.cb.cbtools.permission.factory

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import com.cb.cbtools.constants.Constants.KEY_NOTIFICATION_RECEIVER
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

class AccessNotification : PermissionHandler {
    override fun isSimplePermission() = false

    override fun getArrayOfPermissionAsk() = emptyArray<String>()

    override fun getPermissionType() = PermissionType.PERMISSION_READ_WRITE_CONTACTS

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing notification"

    override fun getPermissionPopUpText() =
        "Notification access is required for core features of this app.\nPlease grant the permission."

    override fun getPermissionButtonText() = "Provide access to Notifications"

    override fun getPermissionIcon() = Icons.Default.Notifications

    override fun askPermission(context: Activity): Unit =
        context.startActivity(
            Intent(
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
            )
        )


    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean {
        return Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )?.let { notificationListenerString ->
            return notificationListenerString.split(":")
                .filter { it.contains("/") }
                .any {
                    it.split("/")[0] == context.packageName &&
                            it.split("/")[1] == data[KEY_NOTIFICATION_RECEIVER]
                }
        } ?: false
    }

}