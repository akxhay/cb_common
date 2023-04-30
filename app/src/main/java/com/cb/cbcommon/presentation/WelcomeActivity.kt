package com.cb.cbcommon.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.cb.cbcommon.AppConstants.appDescRes
import com.cb.cbcommon.AppConstants.appIconRes
import com.cb.cbcommon.AppConstants.appNameRes
import com.cb.cbcommon.AppConstants.home
import com.cb.cbcommon.AppConstants.requiredPermissionOnStartup
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.presentation.composable.WelcomeScreen
import com.cb.cbtools.presentation.viewModel.PermissionViewModel
import com.cb.cbtools.util.PermissionUtil.getPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {

    private val viewModel: PermissionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme {
                WelcomeScreen(
                    context = this@WelcomeActivity,
                    appName = getString(appNameRes),
                    appDesc = getString(appDescRes),
                    appIcon = appIconRes,
                    onclickSkip = {
                        getPermissions()
                    },
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

