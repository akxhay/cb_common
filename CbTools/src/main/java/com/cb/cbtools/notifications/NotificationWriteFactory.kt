package com.cb.cbtools.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationWriteFactory {
    fun getNotificationWriterByAndroidVersion(
        context: Context,
        notificationManager: NotificationManager
    ): SendNotification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GreaterThanOreo(context, notificationManager)
        } else {
            LessThanOreo(context, notificationManager)
        }
    }
}