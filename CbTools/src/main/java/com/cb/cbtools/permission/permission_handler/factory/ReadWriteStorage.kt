package com.cb.cbtools.permission.permission_handler.factory

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import androidx.core.app.ActivityCompat
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.permission.permission_handler.PermissionHandler

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


    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

}