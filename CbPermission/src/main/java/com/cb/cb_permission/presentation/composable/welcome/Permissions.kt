package com.cb.cb_permission.presentation.composable.welcome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.cb.cb_permission.constants.ConstantSetUp
import com.cb.cb_permission.constants.Constants
import com.cb.cb_permission.presentation.utils.PermissionUtil
import com.cb.cb_permission.presentation.utils.ShowAlert
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

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


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionDialog(
    context: Activity,
    currentPermission: String,
    showPermissionAlert: MutableState<Boolean>,
) {
    var text: String? = null
    val title = getDialogTitle(currentPermission)
    var action: () -> Unit = getDialogAction(context, currentPermission)

    val permissionType = ConstantSetUp.getPermissionResolver()[currentPermission]
    if (permissionType == Constants.simplePermission || (permissionType == Constants.manageExternalStoragePermission && Build.VERSION.SDK_INT < Build.VERSION_CODES.R)) {
        val cameraPermissionState = rememberPermissionState(
            ConstantSetUp.getPermissionAskMap()[currentPermission]!![0]
        )
        if (!cameraPermissionState.hasPermission) {
            if (cameraPermissionState.shouldShowRationale) {
                text = "The camera is important for this app. Please grant the permission."
                action = {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts(PACKAGE, context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            } else {
                text = "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
                action = { cameraPermissionState.launchPermissionRequest() }
            }
        }
    } else {
        text = getDialogText(currentPermission)
    }

    ShowAlert(
        text = text,
        title = title,
        showAlert = showPermissionAlert
    ) {
        action()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun getDialogAction(
    context: Activity,
    currentPermission: String,
): () -> Unit {
    return {
        when (ConstantSetUp.getPermissionResolver()[currentPermission]) {
            Constants.manageExternalStoragePermission -> {
                PermissionUtil.requestManageAllStorageAccess(context)
            }
            Constants.notificationAccess -> {
                PermissionUtil.requestNotificationAccess(context)
            }
            Constants.batteryOptimization -> {
                PermissionUtil.requestIgnoreBatteryOptimization(context)
            }
        }

    }
}

fun getDialogText(currentPermission: String): String {
    return ConstantSetUp.getPermissionPopUpText()[currentPermission]!!
}


fun getDialogTitle(currentPermission: String): String {
    return ConstantSetUp.getPermissionPopUpTitle()[currentPermission]!!
}
