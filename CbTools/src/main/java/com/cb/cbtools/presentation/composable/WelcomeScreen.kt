package com.cb.cbtools.presentation.composable

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.factory.PermissionHandlerFactory
import com.cb.cbtools.presentation.common.CbDecisionDialog
import com.cb.cbtools.presentation.common.permission.CbPermissionPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


const val animationDuration = 1000


@Composable
fun WelcomeScreen(
    appName: String,
    appDesc: String,
    version: String,
    @DrawableRes appIcon: Int,
    currentPermission: CbPermission,
    onclickSkip: () -> Unit,
    onPermissionClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    tertiaryTextColor: Color = MaterialTheme.colorScheme.primary,
    cardColor: Color = MaterialTheme.colorScheme.primary,
    cardTextColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            CbPermissionPage(
                appIcon = appIcon,
                appName = appName,
                appDesc = appDesc,
                version = version,
                onclickSkip = onclickSkip,
                currentPermission = currentPermission,
                backgroundColor = backgroundColor,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                tertiaryTextColor = tertiaryTextColor,
                cardColor = cardColor,
                cardTextColor = cardTextColor,
                onPermissionClick = onPermissionClick
            )
        }
}



@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionDialog(
    appName: String,
    context: Activity,
    currentPermission: CbPermission,
    dismiss: () -> Unit,
) {
    val permissionHandler =
        PermissionHandlerFactory.getHandlerForPermission(currentPermission.permissionType)
    var action: () -> Unit? = { permissionHandler!!.askPermission(context) }
    val confirmButtonText = remember {
        mutableStateOf("Confirm")
    }

    if (permissionHandler!!.isSimplePermission() &&
        permissionHandler.getPermissionToCheck() != null
    ) {
        val permissionState = rememberPermissionState(
            permissionHandler.getPermissionToCheck()!!
        )
        if (!permissionState.status.isGranted) {
            if (permissionState.status.shouldShowRationale) {
                confirmButtonText.value = "Allow from settings"
                action = {
                    Toast.makeText(
                        context,
                        "Please Enable " + currentPermission.permissionType.type + " permission for " + appName,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
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
        onConfirmClick = {
            dismiss()
            action()
        },
        onDismissClick = {
            dismiss()
        },
        title = permissionHandler.getPermissionPopUpTitle(),
        text = permissionHandler.getPermissionPopUpText(),
        confirmText = confirmButtonText.value,
    )
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PermissionSkipDialog(
    context: Activity,
    currentPermission: CbPermission,
    dismiss: () -> Unit,
) {
    CbDecisionDialog(
        onConfirmClick = {
            PermissionHandlerFactory.getHandlerForPermission(currentPermission.permissionType)!!
                .skipPermission(context)
            dismiss()
        },
        onDismissClick = {
            dismiss()
        },
        title = "Skip Optional permission",
        text = "Some feature might not work as expected",
        confirmText = "Skip anyway",
    )
}

