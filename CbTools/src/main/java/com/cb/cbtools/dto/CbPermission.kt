package com.cb.cbtools.dto

import com.cb.cbtools.permission.constants.PermissionType

data class CbPermission(
    val permissionType: PermissionType,
    val canBeSkipped: Boolean = false,
    val data: Map<String, Any> = HashMap()
) {
    override fun toString(): String {
        return "CbPermission(permissionType=$permissionType, canBeSkipped=$canBeSkipped, data=$data)"
    }
}