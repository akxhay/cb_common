package com.cb.cbtools.notifications

import android.content.Intent

interface SendNotification {
    fun showNotification(
        title: String,
        text: String,
        intent: Intent,
        flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK,
        notificationId: Int = GENERAL_NOTIFICATION_ID,
        notificationChannelId: String,
        smallIcon: Int
    )

    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 420
        const val GENERAL_NOTIFICATION_ID = 1420

    }
}