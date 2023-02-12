package com.cb.cbtools.permission.permission_handler.factory

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.permission.permission_handler.PermissionHandler


class OptimizeBattery : PermissionHandler {
    override fun isSimplePermission() = false

    override fun getArrayOfPermissionAsk() = emptyArray<String>()

    override fun getPermissionType() = PermissionType.PERMISSION_BATTERY_OPTIMIZE

    override fun getPermissionPopUpTitle() = "Ignore Battery optimization"

    override fun getPermissionPopUpText() =
        "Battery optimization is important for this app to run in background.\nPlease grant the permission."

    override fun getPermissionButtonText() = "Ignore Battery optimization"

    override fun getPermissionIcon() = Icons.Default.BatteryAlert

    @SuppressLint("BatteryLife")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun askPermission(context: Activity): Unit =
        context.startActivity(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).also {
            it.data = Uri.parse("package:" + context.packageName)
        })


    override fun isPermitted(context: Activity, data: Map<String, Any>): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (context.application
                .getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(
                context.packageName
            )
        } else {
            true
        }


}