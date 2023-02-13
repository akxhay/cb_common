package com.cb.cbtools.permission.factory

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

class ReadWriteStorage : PermissionHandler {
    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getPermissionType() = PermissionType.PERMISSION_READ_WRITE_STORAGE

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing storage"

    override fun getPermissionPopUpText() =
        "Storage access is important for this app. Please grant the permission."

    override fun getPermissionButtonText() = "Provide access to storage"

    override fun getPermissionIcon() = Icons.Default.Storage


}