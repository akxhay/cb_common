package com.cb.cbcommon.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.R
import com.cb.cbcommon.notification.NotificationReceiver
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.composables.permission.CbPermission.WelcomeScreen
import com.cb.cbtools.dto.CbPermission
import com.cb.cbtools.permission.PermissionUtil.getPermission
import com.cb.cbtools.permission.constants.Constants.KEY_NOTIFICATION_RECEIVER
import com.cb.cbtools.permission.constants.PermissionType
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


@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private val viewModel: PermissionViewModel by viewModels()

    private val appNameRes: Int = R.string.app_name
    private val appDescRes: Int = R.string.app_desc
    private val appIconRes: Int = R.drawable.play_store_512

    private fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissions()
        setContent {
            CbCommonTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BaseApplication.getInstance().dynamicConfig.getBackgroundColor()
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val currentPermission by viewModel.currentPermission.observeAsState(initial = null as CbPermission?)
                        currentPermission?.let {
                            WelcomeScreen(
                                context = this@WelcomeActivity,
                                currentPermission = it,
                                appIcon = appIconRes,
                                appName = getString(appNameRes),
                                appDesc = getString(appDescRes),
                                onclickSkip = {
                                    getPermissions()
                                },
                                dynamicConfig = BaseApplication.getInstance().dynamicConfig
                            )
                        }
                    }
                }

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
}

