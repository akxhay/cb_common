package com.cb.cbtools.permission.permission_handler.factory

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMissed
import androidx.core.app.ActivityCompat
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.permission.permission_handler.PermissionHandler

class ReadCallLogs : PermissionHandler {

    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.READ_CALL_LOG
    )

    override fun getPermissionType() = PermissionType.PERMISSION_READ_CALL_LOGS

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing call logs"

    override fun getPermissionPopUpText() =
        "Call logs is important for this app. Please grant the permission."

    override fun getPermissionButtonText() = "Provide access to call logs"

    override fun getPermissionIcon() = Icons.Default.CallMissed

    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CALL_LOG
    ) == PackageManager.PERMISSION_GRANTED

}