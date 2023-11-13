package com.cb.cbtools.permission.factory

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMissed
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

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

    override fun getPermissionIcon() = Icons.AutoMirrored.Filled.CallMissed

}