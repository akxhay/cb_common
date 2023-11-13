
package com.cb.cbcommon.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.AppConstants.PREF_RATE_FLAG
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.presentation.screen.CbApp
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbtools.presentation.common.RatePopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var darkTheme by mutableStateOf(false)

    private var showRatePopUp by mutableStateOf(false)

    private val toggleDarkMode: () -> Unit = {
        darkTheme = !darkTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CbApp(
                        navController = rememberNavController(),
                        activity=this@MainActivity,
                        darkTheme=darkTheme,
                        toggleDarkMode = toggleDarkMode
                    )
                    if (showRatePopUp)
                        RatePopUp(
                            dismiss = { showRatePopUp = false },
                            activity = this@MainActivity,
                        )
                }
            }
        }
    }



    override fun onResume() {
        super.onResume()
        if (BaseApplication.getInstance().dynamicConfig.getSharedPreferences().getBoolean(
                PREF_RATE_FLAG,
                true
            )
        ) {
            showRatePopUp = true
        }
    }
}