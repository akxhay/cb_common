package com.cb.cbtools.permission.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.cb.cb_permission.constants.Constants
import com.cb.cbtools.permission.constants.ConstantSetUp.canPermissionSkipped
import com.cb.cbtools.permission.constants.ConstantSetUp.getPermissionAskMap
import com.cb.cbtools.permission.constants.ConstantSetUp.getPermissionResolver
import com.cb.cbtools.permission.presentation.utils.CustomToast.showToast

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
            )
            notificationListenerString != null && notificationListenerString.contains(context.packageName)
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

    fun requestNotificationAccess(
        context: Activity,
        appName: String?,
        navigateAnyways: Boolean
    ) {
        try {
            val notificationListenerString = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            )
            //Check notifications access permission
            if (navigateAnyways || (notificationListenerString == null || !notificationListenerString.contains(
                    context.packageName
                ))
            ) {
                if (!navigateAnyways) {
                    showToast(context, "Please Enable Notification access for $appName")
                }
                val intent = Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
                )
                context.startActivity(intent)
            } else {
                showToast(context, "Already permitted")
            }
        } catch (e: Exception) {
            val linNumber = Thread.currentThread().stackTrace[2].lineNumber
        }
    }

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
                showToast(context, "Already permitted")
            }
        } catch (e: Exception) {
            val linNumber = Thread.currentThread().stackTrace[2].lineNumber
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(requiredPermissionOnStartup: ArrayList<String>, context: Activity): String {

        val iterator = requiredPermissionOnStartup.iterator()
        while (iterator.hasNext()) {
            val permission = iterator.next()
            if (isPermittedByType(
                    context,
                    permission
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

