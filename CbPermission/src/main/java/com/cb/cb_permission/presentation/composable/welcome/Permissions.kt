package com.cb.cb_permission.presentation.composable.welcome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cb.cb_permission.constants.ConstantSetUp
import com.cb.cb_permission.constants.Constants
import com.cb.cb_permission.presentation.utils.CustomToast
import com.cb.cb_permission.presentation.utils.PermissionUtil
import com.cb.cb_permission.presentation.utils.ShowAlert
import com.google.accompanist.permissions.*

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
    appName: String,
    context: Activity,
    currentPermission: String,
    showPermissionAlert: MutableState<Boolean>,
) {
    val text = getDialogText(currentPermission)
    val title = getDialogTitle(currentPermission)
    var action: () -> Unit = getDialogAction(appName, context, currentPermission)
    val confirmButtonText = remember {
        mutableStateOf("Confirm")
    }

    val permissionType = ConstantSetUp.getPermissionResolver()[currentPermission]
    if (permissionType == Constants.simplePermission || (permissionType == Constants.manageExternalStoragePermission && Build.VERSION.SDK_INT < Build.VERSION_CODES.R)) {
        val permissionState = rememberPermissionState(
            ConstantSetUp.getPermissionAskMap()[currentPermission]!![0]
        )
        if (!permissionState.status.isGranted) {
            if (permissionState.status.shouldShowRationale) {
                confirmButtonText.value = "Allow from settings"
                action = {
                    CustomToast.showToast(
                        context,
                        "Please Enable " + ConstantSetUp.getPermissionType()[currentPermission] + " permission for " + appName
                    )
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts(PACKAGE, context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            } else {
                action = { permissionState.launchPermissionRequest() }
            }
        }
    }

    ShowAlert(
        text = text,
        title = title,
        showAlert = showPermissionAlert,
        confirmButtonText = confirmButtonText.value
    ) {
        action()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun getDialogAction(
    appName: String,
    context: Activity,
    currentPermission: String,
    navigateAnyways: Boolean = false
): () -> Unit {
    return {
        when (ConstantSetUp.getPermissionResolver()[currentPermission]) {
            Constants.manageExternalStoragePermission -> {
                PermissionUtil.requestManageAllStorageAccess(context)
            }
            Constants.notificationAccess -> {
                PermissionUtil.requestNotificationAccess(context, appName, navigateAnyways)
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
