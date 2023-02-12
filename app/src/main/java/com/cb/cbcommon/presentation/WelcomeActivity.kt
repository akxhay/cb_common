package com.cb.cbcommon.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.R
import com.cb.cbcommon.notification.NotificationReceiver
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.PermissionUtil.getPermission
import com.cb.cbtools.permission.constants.Constants.KEY_NOTIFICATION_RECEIVER
import com.cb.cbtools.permission.constants.PermissionType
import com.cb.cbtools.presentation.composable.WelcomeScreen
import com.cb.cbtools.presentation.viewModel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint

private val requiredPermissionOnStartup = arrayListOf(
    CbPermission(
        permissionType = PermissionType.PERMISSION_ACCESS_NOTIFICATION,
        data = mapOf(
            KEY_NOTIFICATION_RECEIVER to NotificationReceiver::class.qualifiedName!!
        )
    ),
    CbPermission(
        permissionType = PermissionType.PERMISSION_POST_NOTIFICATION,
        canBeSkipped = true
    ),
    CbPermission(
        permissionType = PermissionType.PERMISSION_BATTERY_OPTIMIZE,
        canBeSkipped = true
    )
)
private const val appNameRes: Int = R.string.app_name
private const val appDescRes: Int = R.string.app_desc
private const val appIconRes: Int = R.drawable.play_store_512

val home = MainActivity::class.java

@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private val viewModel: PermissionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme() {
                WelcomeScreen(
                    context = this@WelcomeActivity,
                    appIcon = appIconRes,
                    appName = getString(appNameRes),
                    appDesc = getString(appDescRes),
                    onclickSkip = {
                        getPermissions()
                    },
                    dynamicConfig = BaseApplication.getInstance().dynamicConfig,
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getPermissions()
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) goToHome()
        else getPermission(requiredPermissionOnStartup, this)?.let { viewModel.changeType(it) }
            ?: goToHome()
    }

    private fun goToHome() {
        startActivity(Intent(this, home))
        finish()
    }
}

