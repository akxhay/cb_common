package com.cb.cbcommon.util.common

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.cb.cbcommon.R
import com.cb.cbcommon.presentation.activity.MainActivity
import com.cb.cbtools.notifications.NotificationWriteFactory
import com.cb.cbtools.notifications.SendNotification

class NotificationWriteHelper(
    val context: Context,
    private val notificationManager: NotificationManager
) {
    private val tag = "NotificationWriteHelper"

    private val notificationWriter: SendNotification =
        NotificationWriteFactory.getNotificationWriterByAndroidVersion(context, notificationManager)

    fun showNotification(
        title: String = "New messages",
        text: String = "New text",
        smallIcon: Int = R.drawable.ic_round_cropped,
        largeIcon: Bitmap? = null
    ) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        notificationWriter.showNotification(
            title = title,
            text = text,
            intent = intent,
            notificationChannelId = NotificationHelper.DEFAULT,
            smallIcon = smallIcon,
            largeIcon = largeIcon
        )
    }


}