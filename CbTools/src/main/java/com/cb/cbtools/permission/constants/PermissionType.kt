package com.cb.cbtools.permission.constants

enum class PermissionType(val type: String) {
    PERMISSION_READ_CONTACTS("Contacts"),
    PERMISSION_READ_WRITE_CONTACTS("Contacts"),
    PERMISSION_READ_CALL_LOGS("Call logs"),
    PERMISSION_READ_WRITE_CALL_LOGS("Call logs"),
    PERMISSION_READ_WRITE_STORAGE("Storage"),
    PERMISSION_MANAGE_STORAGE("Manage all files"),
    PERMISSION_BATTERY_OPTIMIZE("Battery optimization"),
    PERMISSION_POST_NOTIFICATION("Notification"),
    PERMISSION_ACCESS_NOTIFICATION("Notification")


}
