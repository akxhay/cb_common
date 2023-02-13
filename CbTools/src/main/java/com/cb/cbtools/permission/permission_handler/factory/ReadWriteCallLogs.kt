package com.cb.cbtools.permission.permission_handler.factory

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMissed
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.permission.permission_handler.PermissionHandler

class ReadWriteCallLogs : PermissionHandler {

    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.READ_CALL_LOG
    )

    override fun getPermissionType() = PermissionType.PERMISSION_READ_WRITE_CALL_LOGS

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing call logs"

    override fun getPermissionPopUpText() =
        "Call logs is important for this app. Please grant the permission."

    override fun getPermissionButtonText() = "Provide access to call logs"

    override fun getPermissionIcon() = Icons.Default.CallMissed

}