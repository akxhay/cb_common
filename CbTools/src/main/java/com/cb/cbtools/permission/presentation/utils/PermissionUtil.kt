package com.cb.cbtools.permission.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.cb.cb_permission.constants.Constants
import com.cb.cbtools.permission.constants.ConstantSetUp.canPermissionSkipped
import com.cb.cbtools.permission.constants.ConstantSetUp.getPermissionAskMap
import com.cb.cbtools.permission.constants.ConstantSetUp.getPermissionResolver

object PermissionUtil {
    private const val CB_PERMISSIONS_SKIPPED = "CB_PERMISSIONS_SKIPPED"

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestManageAllStorageAccess(
        context: Activity,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isPermittedByType(
        context: Activity,
        permission: String,
        permissionData: Map<String, Any?> = HashMap(),
    ): Boolean {

        val resolver = getPermissionResolver()[permission]
        if (Constants.simplePermission == resolver) {
            return isPermitted(context, getPermissionAskMap()[permission]!![0])
        }
        return if (Constants.manageExternalStoragePermission == resolver) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                isPermitted(
                    context, getPermissionAskMap()[permission]!![0]
                )
            }
        } else if (Constants.notificationAccess == resolver) {
            val notificationListenerString = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            ) ?: return false
            if (!notificationListenerString.contains(":")) return false

            val services = notificationListenerString.split(":")
            services.filter { e -> e.contains("/") }.filter { e ->
                e.split("/")[0] == context.packageName
                        && e.split("/")[1] == permissionData[Constants.notificationAccess]
            }.isNotEmpty()
        } else if (Constants.batteryOptimization == resolver) {
            (context.application
                .getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(
                context.packageName
            )
        } else false
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isPermitted(context: Activity, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestNotificationAccess(
        context: Activity,
        appName: String?,
        navigateAnyways: Boolean
    ) {
        try {

            if (navigateAnyways || !isPermitted(context, Constants.notificationAccess)) {
                if (!navigateAnyways) {
                    Toast.makeText(
                        context,
                        "Please Enable Notification access for $appName",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val intent = Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
                )
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Already permitted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            val linNumber = Thread.currentThread().stackTrace[2].lineNumber
        }
    }

    @SuppressLint("BatteryLife")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestIgnoreBatteryOptimization(context: Activity) {
        try {
            if (!(context.application
                    .getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(
                    context.packageName
                )
            ) {
                val intent: Intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    .setData(Uri.parse("package:" + context.packageName))
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Already permitted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            val linNumber = Thread.currentThread().stackTrace[2].lineNumber
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(
        requiredPermissionOnStartup: ArrayList<String>,
        context: Activity,
        permissionData: Map<String, Any?>
    ): String {

        val iterator = requiredPermissionOnStartup.iterator()
        while (iterator.hasNext()) {
            val permission = iterator.next()
            if (isPermittedByType(
                    context,
                    permission,
                    permissionData
                )
            ) {
                iterator.remove()
            }
        }
        return if (requiredPermissionOnStartup.isEmpty() || isPermissionSkipped(
                requiredPermissionOnStartup[0], context
            )
        ) {
            ""
        } else {
            requiredPermissionOnStartup[0]
        }
    }

    private fun isPermissionSkipped(currentPermission: String, context: Activity): Boolean {
        return canPermissionSkipped()[currentPermission]!! && context.getSharedPreferences(
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

