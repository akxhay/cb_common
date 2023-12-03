package com.cb.cbcommon.util.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat

object NotificationHelper {
    const val DEFAULT = "default"
    const val REPLY_CHANNEL_ID = "Direct reply"

    fun initializeChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        val persistentChannel = NotificationChannel(
            DEFAULT,
            DEFAULT,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager!!.createNotificationChannel(persistentChannel)

        val channel = NotificationChannel(
            REPLY_CHANNEL_ID,
            "Direct Reply Channel",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for direct replies"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
        }


        manager.createNotificationChannel(channel)

    }
}