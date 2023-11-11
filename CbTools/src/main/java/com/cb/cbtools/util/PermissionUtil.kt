package com.cb.cbtools.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.factory.PermissionHandlerFactory

object PermissionUtil {
    private const val TAG = "PermissionUtil"
    private const val CB_PERMISSIONS_SKIPPED = "CB_PERMISSIONS_SKIPPED"

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(
        permissions: List<CbPermission>,
        context: Activity,
    ): CbPermission? {
        for (permission in permissions) {
            Log.d(TAG, "getPermission : $permission")
            PermissionHandlerFactory.getHandlerForPermission(permission.permissionType)?.let {
                if (it.isPermitted(context, permission.data)) return@let
                Log.d(TAG, "isPermitted : false")
                if (permission.canBeSkipped && it.isSkipped(context)) return@let
                Log.d(TAG, "returning : $permission")
                return permission
            }
        }
        Log.d(TAG, "returning : null")
        return null
    }

    fun isPermissionSkipped(currentPermission: String, context: Activity): Boolean {
        return context.getSharedPreferences(
            CB_PERMISSIONS_SKIPPED,
            Context.MODE_PRIVATE
        )
            .getBoolean(currentPermission, false)

    }

    fun skipPermission(currentPermission: String, context: Activity) {
        context.getSharedPreferences(CB_PERMISSIONS_SKIPPED, Context.MODE_PRIVATE).edit()
            .putBoolean(currentPermission, true).apply()
    }
}

