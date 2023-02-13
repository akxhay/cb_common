package com.cb.cbtools.permission.factory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

class ManageExternalStorage : PermissionHandler {
    override fun isSimplePermission() = Build.VERSION.SDK_INT < Build.VERSION_CODES.R

    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getPermissionType() = PermissionType.PERMISSION_MANAGE_STORAGE

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

    override fun askPermission(context: Activity): Unit? =
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.startActivity(Intent().also {
                it.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                it.data = Uri.fromParts("package", context.packageName, null)
            })
        } else {
            super.askPermission(context)
        })


}