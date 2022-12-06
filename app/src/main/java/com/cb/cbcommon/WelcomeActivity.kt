package com.cb.cbcommon

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cb.cb_permission.constants.Constants.batteryOptimization
import com.cb.cb_permission.constants.Constants.notificationAccess
import com.cb.cb_permission.constants.Constants.postNotification
import com.cb.cb_permission.presentation.composable.CbPermission.WelcomeScreen
import com.cb.cb_permission.presentation.utils.PermissionUtil.getPermission
import com.cb.cbcommon.ui.theme.CbCommonTheme

private val requiredPermissionOnStartup = arrayListOf(
    notificationAccess,
    postNotification,
    batteryOptimization
)


class WelcomeActivity : ComponentActivity() {
    private val appNameRes: Int = R.string.app_name
    private val appDescRes: Int = R.string.app_desc
    private val appIconRes: Int = R.drawable.play_store_512


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissions()
    }

    override fun onResume() {
        super.onResume()
        getPermissions()
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getScreen()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getScreen() {
        val currentPermission = getPermission(requiredPermissionOnStartup, this)
        if (currentPermission != "") {
            setContent {
                CbCommonTheme() {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        WelcomeScreen(
                            context = this,
                            currentPermission = currentPermission,
                            appIcon = appIconRes,
                            appName = getString(appNameRes),
                            appDesc = getString(appDescRes),
                            onclickSkip = {
                                Toast.makeText(
                                    this, "Skipping optional permissions", Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            },
                        )
                    }
                }
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}

