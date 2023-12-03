package com.cb.cbtools.notifications

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import com.cb.cbtools.R

class LessThanOreo(
    private val context: Context,
    private val notificationManager: NotificationManager
) : SendNotification {
    @SuppressLint("ResourceAsColor")
    override fun showNotification(
        title: String,
        text: String,
        intent: Intent,
        flag: Int,
        notificationId: Int,
        notificationChannelId: String,
        smallIcon: Int,
        largeIcon: Bitmap?,
        replyAction: NotificationCompat.Action?
    ) {
        val pIntent = PendingIntent.getActivity(context, notificationId, intent, 0)
        val mBuilder = NotificationCompat.Builder(context, notificationChannelId)
        mBuilder.setSmallIcon(smallIcon)
        mBuilder.setLargeIcon(largeIcon)
        mBuilder.color = R.color.black
        mBuilder.setContentIntent(pIntent)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
        mBuilder.setAutoCancel(true)
        mBuilder.addAction(replyAction)
        notificationManager.notify(notificationId, mBuilder.build())
    }
}