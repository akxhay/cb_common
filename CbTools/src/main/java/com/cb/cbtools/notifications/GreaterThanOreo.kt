package com.cb.cbtools.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class GreaterThanOreo(
    private val context: Context,
    private val notificationManager: NotificationManager
) : SendNotification {
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun showNotification(
        title: String,
        text: String,
        intent: Intent,
        flag: Int,
        notificationId: Int,
        notificationChannelId: String,
        smallIcon: Int
    ) {
        notificationManager.notify(
            notificationId,
            NotificationCompat.Builder(context, notificationChannelId)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        notificationId,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(smallIcon)
                .setChannelId(notificationChannelId)
                .setContentTitle(title)
                .setContentText(text).build()
        )
    }

}