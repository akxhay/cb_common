package com.cb.cbtools.permission.presentation.composable.welcome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cb.cb_permission.constants.Constants
import com.cb.cbcommon.DynamicConfig
import com.cb.cbtools.composables.CbDecisionDialog
import com.cb.cbtools.permission.constants.ConstantSetUp
import com.cb.cbtools.permission.presentation.utils.PermissionUtil
import com.google.accompanist.permissions.*

private const val PACKAGE = "package"
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionDialog(
    appName: String,
    context: Activity,
    currentPermission: String,
    showPermissionAlert: MutableState<Boolean>,
    dynamicConfig: DynamicConfig,
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
                    Toast.makeText(
                        context,
                        "Please Enable " + ConstantSetUp.getPermissionType()[currentPermission] + " permission for " + appName,
                        Toast.LENGTH_SHORT
                    ).show()
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


    CbDecisionDialog(
        showAlert = showPermissionAlert,
        onConfirmClick = action,
        title = title,
        text = text,
        confirmText = confirmButtonText.value,
        dynamicConfig = dynamicConfig
    )
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
