package com.cb.cbtools.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.util.PermissionUtil


interface PermissionHandler {
    fun isSimplePermission(): Boolean = true

    fun getArrayOfPermissionAsk(): Array<String>

    fun getPermissionToCheck(): String? =
        if (getArrayOfPermissionAsk().isNotEmpty()) getArrayOfPermissionAsk()[0] else null

    fun getPermissionType(): PermissionType

    fun askPermission(context: Activity): Unit? = null

    fun getPermissionPopUpTitle(): String

    fun getPermissionPopUpText(): String

    fun getPermissionButtonText(): String

    fun getPermissionIcon(): ImageVector

    fun isPermitted(context: Activity, data: Map<String, Any> = HashMap()): Boolean =
        getPermissionToCheck()?.let {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        } ?: false

    fun skipPermission(context: Activity) =
        PermissionUtil.skipPermission(getPermissionType().name, context)

    fun isSkipped(context: Activity) =
        PermissionUtil.isPermissionSkipped(getPermissionType().name, context)

}