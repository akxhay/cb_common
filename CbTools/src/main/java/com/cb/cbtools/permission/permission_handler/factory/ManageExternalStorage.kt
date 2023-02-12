package com.cb.cbtools.permission.permission_handler.factory

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Environment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.permission.permission_handler.PermissionHandler

class ManageExternalStorage : PermissionHandler {
    override fun isSimplePermission() = Build.VERSION.SDK_INT < Build.VERSION_CODES.R

    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_CONTACTS
    )

    override fun getPermissionType() = PermissionType.PERMISSION_READ_WRITE_CONTACTS

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing storage"

    override fun getPermissionPopUpText() =
        "Storage access is important for this app. Please grant the permission."

    override fun getPermissionButtonText() = "Provide access to storage"

    override fun getPermissionIcon() = Icons.Default.Storage

    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            super.isPermitted(context, data)
        }
    }


}