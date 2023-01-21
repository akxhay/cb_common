package com.cb.cbtools.permission.constants

import android.Manifest
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.cb.cb_permission.constants.Constants
import com.cb.cbtools.R

object ConstantSetUp {
    fun getPermissionAskMap(): Map<String, Array<String>> {
        val map: MutableMap<String, Array<String>> = HashMap()
        map[Constants.readWriteContacts] = arrayOf(
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS
        )
        map[Constants.readCallLog] = arrayOf(Manifest.permission.READ_CALL_LOG)
        map[Constants.readWriteCallLog] = arrayOf(
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.READ_CALL_LOG
        )
        map[Constants.writeExternalStorage] = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        map[Constants.manageExternalStoragePermission] = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        map[Constants.postNotification] = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

        return map
    }

    fun getPermissionCheckMap(): Map<String, Array<String>> {
        val map: MutableMap<String, Array<String>> = HashMap()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            map[Constants.readWriteContacts] = arrayOf(Manifest.permission.READ_CONTACTS)
            map[Constants.readWriteCallLog] = arrayOf(Manifest.permission.READ_CALL_LOG)
        } else {
            map[Constants.readWriteCallLog] = arrayOf(
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.READ_CALL_LOG
            )
            map[Constants.readWriteContacts] = arrayOf(
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS
            )
        }
        map[Constants.readCallLog] = arrayOf(Manifest.permission.READ_CALL_LOG)
        map[Constants.writeExternalStorage] = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        map[Constants.manageExternalStoragePermission] = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return map
    }

    fun getPermissionResolver(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map[Constants.readWriteContacts] = Constants.simplePermission
        map[Constants.readCallLog] = Constants.simplePermission
        map[Constants.readWriteCallLog] = Constants.simplePermission
        map[Constants.writeExternalStorage] = Constants.simplePermission
        map[Constants.postNotification] = Constants.simplePermission

        map[Constants.manageExternalStoragePermission] =
            Constants.manageExternalStoragePermission
        map[Constants.batteryOptimization] = Constants.batteryOptimization
        map[Constants.notificationAccess] = Constants.notificationAccess

        return map
    }

    fun getPermissionType(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map[Constants.readWriteContacts] = "Contacts"
        map[Constants.readCallLog] = "Call logs"
        map[Constants.readWriteCallLog] = "Call logs"
        map[Constants.writeExternalStorage] = "Storage"
        map[Constants.manageExternalStoragePermission] = "Manage all files"
        map[Constants.batteryOptimization] = "Battery optimization"
        map[Constants.notificationAccess] = "Notification"
        map[Constants.postNotification] = "Notification"

        return map
    }

    fun getPermissionPopUpTitle(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map[Constants.readWriteContacts] = "Provide permissions for accessing contacts"
        map[Constants.readCallLog] = "Provide permissions for accessing call logs"
        map[Constants.readWriteCallLog] = "Provide permissions for accessing call logs"
        map[Constants.writeExternalStorage] = "Provide permissions for accessing storage"
        map[Constants.manageExternalStoragePermission] = "Provide permissions to manage all files"
        map[Constants.batteryOptimization] = "Ignore Battery optimization"
        map[Constants.notificationAccess] = "Provide permissions for accessing notification"
        map[Constants.postNotification] = "Provide permissions for sending notification"

        return map
    }

    fun getPermissionPopUpText(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map[Constants.notificationAccess] =
            "Notification access is required for core features of this app.\nPlease grant the permission."
        map[Constants.postNotification] =
            "Foreground notification permission is required from Android 13 and above.\nPlease grant the permission."
        map[Constants.batteryOptimization] =
            "Battery optimization is important for this app to run in background.\nPlease grant the permission."

        map[Constants.readWriteContacts] =
            "Contact access is important for this app. Please grant the permission."
        map[Constants.readCallLog] =
            "Call logs access is important for this app. Please grant the permission."
        map[Constants.readWriteCallLog] =
            "Call logs is important for this app. Please grant the permission."
        map[Constants.writeExternalStorage] =
            "Storage access is important for this app. Please grant the permission."
        map[Constants.manageExternalStoragePermission] =
            "Storage access is important for this app. Please grant the permission."
        return map
    }

    fun getPermissionButtonText(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map[Constants.readWriteContacts] = "Provide access to Contacts"
        map[Constants.readCallLog] = "Provide access to call logs"
        map[Constants.readWriteCallLog] = "Provide access to call logs"
        map[Constants.writeExternalStorage] = "Provide access to storage"
        map[Constants.manageExternalStoragePermission] = "Provide access to all files"
        map[Constants.batteryOptimization] = "Ignore Battery optimization"
        map[Constants.notificationAccess] = "provide access to Notifications"
        map[Constants.postNotification] = "Allow us to notify you"

        return map
    }

    fun getPermissionDrawable(): Map<String, Int> {
        val map: MutableMap<String, Int> = HashMap()
        map[Constants.readWriteContacts] = R.drawable.ic_permission_contact
        map[Constants.readCallLog] = R.drawable.ic_permission_call
        map[Constants.readWriteCallLog] = R.drawable.ic_permission_call
        map[Constants.writeExternalStorage] = R.drawable.ic_permission_storage
        map[Constants.manageExternalStoragePermission] = R.drawable.ic_permission_storage
        map[Constants.batteryOptimization] = R.drawable.ic_permission_battery
        map[Constants.notificationAccess] = R.drawable.ic_permission_notification
        map[Constants.postNotification] = R.drawable.ic_permission_notification

        return map
    }

    fun getPermissionIcon(): Map<String, ImageVector> {
        val map: MutableMap<String, ImageVector> = HashMap()
        map[Constants.readWriteContacts] = Icons.Default.Contacts
        map[Constants.readCallLog] = Icons.Default.CallMissed
        map[Constants.readWriteCallLog] = Icons.Default.CallMissed
        map[Constants.writeExternalStorage] = Icons.Default.Storage
        map[Constants.manageExternalStoragePermission] = Icons.Default.Storage
        map[Constants.batteryOptimization] = Icons.Default.BatteryAlert
        map[Constants.notificationAccess] = Icons.Default.Notifications
        map[Constants.postNotification] = Icons.Default.NotificationsActive

        return map
    }

    fun canPermissionSkipped(): Map<String, Boolean> {
        val map: MutableMap<String, Boolean> = HashMap()
        map[Constants.notificationAccess] = false
        map[Constants.postNotification] = false
        map[Constants.batteryOptimization] = true

        map[Constants.readWriteContacts] = false
        map[Constants.readCallLog] = false
        map[Constants.readWriteCallLog] = false
        map[Constants.writeExternalStorage] = false
        map[Constants.manageExternalStoragePermission] = false


        return map
    }
}