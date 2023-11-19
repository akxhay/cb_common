
package com.cb.cbcommon.presentation.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.data.constant.AppConstants.PREF_RATE_FLAG
import com.cb.cbcommon.presentation.CbApp
import com.cb.cbcommon.presentation.theme.CbCommonTheme
import com.cb.cbcommon.util.common.AppSetup
import com.cb.cbcommon.util.common.AppSetup.getDynamicColor
import com.cb.cbcommon.util.common.AppSetup.getSystemTheme
import com.cb.cbtools.presentation.common.RatePopUp
import com.cb.cbtools.presentation.common.ThemePopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var currentTheme by mutableIntStateOf(getSystemTheme())
    private var currentDynamicColor by mutableStateOf(getDynamicColor())

    private var showRatePopUp by mutableStateOf(false)
    private var systemThemePopUp by mutableStateOf(false)

    private val updateTheme: (Int, Boolean) -> Unit = { th, dc ->
        currentTheme = th
        currentDynamicColor = dc
        AppSetup.updateSystemTheme(currentTheme)
        AppSetup.updateDynamicColor(currentDynamicColor)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CbCommonTheme(
                darkTheme = if (currentTheme == 0) isSystemInDarkTheme() else currentTheme == 1,
                dynamicColor = currentDynamicColor
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CbApp(
                        navController = rememberNavController(),
                        activity = this@MainActivity,
                        theme = currentTheme,
                        toggleDarkMode = {
                            systemThemePopUp = true
                        },
                    )
                    if (showRatePopUp)
                        RatePopUp(
                            dismiss = { showRatePopUp = false },
                            activity = this@MainActivity,
                        )

                    if (systemThemePopUp)
                        ThemePopUp(
                            currentTheme = currentTheme,
                            currentDynamicColor = currentDynamicColor,
                            dynamicColorApplicable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                            updateTheme = updateTheme
                        ) { systemThemePopUp = false }
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