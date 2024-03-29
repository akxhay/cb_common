package com.cb.cbtools.permission.factory

import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

object PermissionHandlerFactory {
    private val handlerMap = mapOf(
        PermissionType.PERMISSION_READ_CONTACTS to ReadContacts(),
        PermissionType.PERMISSION_READ_WRITE_CONTACTS to ReadWriteContacts(),
        PermissionType.PERMISSION_READ_CALL_LOGS to ReadCallLogs(),
        PermissionType.PERMISSION_READ_WRITE_CALL_LOGS to ReadWriteCallLogs(),
        PermissionType.PERMISSION_READ_WRITE_STORAGE to ReadWriteStorage(),
        PermissionType.PERMISSION_MANAGE_STORAGE to ManageExternalStorage(),
        PermissionType.PERMISSION_BATTERY_OPTIMIZE to OptimizeBattery(),
        PermissionType.PERMISSION_POST_NOTIFICATION to PostNotification(),
        PermissionType.PERMISSION_ACCESS_NOTIFICATION to AccessNotification(),

        )


    fun getHandlerForPermission(permissionType: PermissionType): PermissionHandler? {
        return handlerMap[permissionType]
    }
}