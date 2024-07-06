package com.cb.cbtools.notifications

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat

interface SendNotification {
    fun showNotification(   
        title: String,
        text: String,
        intent: Intent,
        flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK,
        notificationId: Int = GENERAL_NOTIFICATION_ID,
        notificationChannelId: String,
        smallIcon: Int,
        largeIcon: Bitmap? = null,
        replyAction: NotificationCompat.Action? = null

    )

    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 420
        const val GENERAL_NOTIFICATION_ID = 1420

    }

}