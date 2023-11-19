package com.cb.cbcommon.util.common

import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.data.constant.AppConstants.SYSTEM_THEME

object AppSetup {

    fun getSystemTheme(): Int {
        return BaseApplication.getInstance().sharedPreferences.getInt(
            SYSTEM_THEME,
            0
        )
    }

    fun updateSystemTheme(darkMode: Int) {
        BaseApplication.getInstance().sharedPreferences.edit()
            .putInt(SYSTEM_THEME, darkMode).apply()
    }


}