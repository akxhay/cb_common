package com.cb.cbcommon.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cb.cbcommon.AppConstants.home
import com.cb.cbcommon.AppConstants.requiredPermissionOnStartup
import com.cb.cbcommon.AppConstants.version
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.presentation.composable.PermissionDialog
import com.cb.cbtools.presentation.composable.PermissionSkipDialog
import com.cb.cbtools.presentation.composable.WelcomeScreen
import com.cb.cbtools.util.PermissionUtil.getPermission
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.M)
@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private var currentPermission by mutableStateOf(requiredPermissionOnStartup.first())
    private var showPermissionAlert by mutableStateOf(false)
    private var showSkipAlert by mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme {
                WelcomeScreen(
                    appName = getString(R.string.app_name),
                    appDesc = getString(R.string.app_desc),
                    appIcon = R.drawable.play_store_512,
                    version = version,
                    onclickSkip = {
                        showSkipAlert = true
                    },
                    currentPermission = currentPermission,
                    onPermissionClick = {
                        showPermissionAlert = true
                    }
                )
                if (showPermissionAlert) {
                    PermissionDialog(
                        appName = getString(R.string.app_name),
                        context = this@WelcomeActivity,
                        currentPermission = currentPermission,
                        dismiss = {
                            showPermissionAlert = false
                        },
                    )
                }
                if (showSkipAlert) {
                    PermissionSkipDialog(
                        context = this@WelcomeActivity,
                        currentPermission = currentPermission,
                        dismiss = {
                            showSkipAlert = false
                            getPermissions()
                        },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getPermissions()
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            goToHome()
        } else {
            getPermission(requiredPermissionOnStartup, this)?.let { permission ->
                currentPermission = permission
            } ?: goToHome()
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, home))
        finish()
    }
}


