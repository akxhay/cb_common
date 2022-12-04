package com.cb.cb_permission.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.cb.cb_permission.R
import com.cb.cb_permission.constants.ConstantSetUp.canPermissionSkipped
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionAskMap
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionPopUpTitle
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionResolver
import com.cb.cb_permission.constants.ConstantSetUp.getPermissionType
import com.cb.cb_permission.constants.Constants
import com.cb.cb_permission.listeners.GenericOnClickListener

object Util {
    private const val CB_PERMISSIONS_SKIPPED = "CB_PERMISSIONS_SKIPPED"
    private const val GRANTED = "GG"
    private const val NOT_GRANTED = "NG"
    private const val DENIED = "DG"
    private const val CANCEL = "Cancel"
    private const val OK = "Ok"
    private const val PACKAGE = "package"
    private const val PRE_TEXT = "Provide permissions for accessing "
    private const val PRE_DENIED_TEXT =
        "You have denied the permission for the first time, please click 'Ok' to go to application settings and provide  permission for "

    private fun requestPermissionDialog(
        context: Activity,
        permissions: Array<String>,
        currentPermission: String
    ) {
        val title = getPermissionPopUpTitle()[currentPermission]!!
        val currStatus = getStatus(context, permissions[0])

        AlertHelper.showMessageAlertWithAction(
            context,
            if (DENIED == currStatus) PRE_DENIED_TEXT + getPermissionType()[currentPermission] else null,
            title,
            object : GenericOnClickListener {
                override fun onCLick() {
                    if (NOT_GRANTED == currStatus) {
                        ActivityCompat.requestPermissions(context, permissions, 421)
                    } else if (DENIED == currStatus) {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts(PACKAGE, context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }
            })
    }

    private fun getStatus(context: Activity, permission: String): String {
        var status = NOT_GRANTED
        status = if (ActivityCompat.checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                NOT_GRANTED
            } else {
                DENIED
            }
        } else {
            GRANTED
        }
        return status
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestPermission(
        context: Activity,
        currentPermission: String,
        permissions: Array<String>,
    ) {
        try {
            if (!isPermitted(context, permissions[0])) {
                requestPermissionDialog(context, permissions, currentPermission)
            } else {
                val inflater: LayoutInflater = context.layoutInflater
                val toastLayout: View =
                    inflater.inflate(R.layout.custom_toast, context.findViewById(R.id.CustomToast))
                val toast = Toast(context)
                val toastText: TextView = toastLayout.findViewById(R.id.ToastText)
                toastText.text = "Already permitted"
                toast.duration = Toast.LENGTH_SHORT
                toast.view = toastLayout
                toast.show()
            }
        } catch (e: Exception) {
            val linNumber = Thread.currentThread().stackTrace[2].lineNumber
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestPermissionByType(
        context: Activity,
        currentPermission: String,
    ) {
        when (getPermissionResolver()[currentPermission]) {
            Constants.simplePermission -> {
                requestPermission(
                    context = context,
                    currentPermission = currentPermission,
                    permissions = getPermissionAskMap()[currentPermission]!!,
                )
            }
            Constants.manageExternalStoragePermission -> {
                requestManageAllStorageAccess(context, currentPermission)
            }
            Constants.notificationAccess -> {
                requestNotificationAccess(context)
            }
            Constants.batteryOptimization -> {
                requestIgnoreBatteryOptimization(context)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestManageAllStorageAccess(
        context: Activity,
        currentPermission: String,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(context, "accessible", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        } else
            requestPermission(
                context = context,
                currentPermission = currentPermission,
                permissions = getPermissionAskMap()[currentPermission]!!,
            )
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

    private fun requestNotificationAccess(context: Activity) {
        try {
            val notificationListenerString = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            )
            //Check notifications access permission
            if (notificationListenerString == null || !notificationListenerString.contains(context.packageName)) {
                val inflater: LayoutInflater = context.layoutInflater
                val toastLayout: View =
                    inflater.inflate(R.layout.custom_toast, context.findViewById(R.id.CustomToast))
                val toast = Toast(context.applicationContext)
                val toastText: TextView = toastLayout.findViewById(R.id.ToastText)
                toastText.text = "Please Enable Notification access for Chat Bin"
                toast.duration = Toast.LENGTH_SHORT
                toast.setView(toastLayout)
                toast.show()
                //The notification access has not acquired yet!
                val intent = Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
                )
                context.startActivity(intent)
            } else {
                val inflater: LayoutInflater = context.layoutInflater
                val toastLayout: View =
                    inflater.inflate(R.layout.custom_toast, context.findViewById(R.id.CustomToast))
                val toast = Toast(context.applicationContext)
                val toastText: TextView = toastLayout.findViewById(R.id.ToastText)
                toastText.text = "Already permitted"
                toast.duration = Toast.LENGTH_SHORT
                toast.setView(toastLayout)
                toast.show()
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
                val inflater: LayoutInflater = context.layoutInflater
                val toastLayout: View =
                    inflater.inflate(R.layout.custom_toast, context.findViewById(R.id.CustomToast))
                val toast = Toast(context.applicationContext)
                val toastText: TextView = toastLayout.findViewById(R.id.ToastText)
                toastText.text = "Already permitted"
                toast.duration = Toast.LENGTH_SHORT
                toast.view = toastLayout
                toast.show()
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

