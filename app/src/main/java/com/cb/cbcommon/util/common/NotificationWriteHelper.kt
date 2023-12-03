package com.cb.cbcommon.util.common

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.cb.cbcommon.R
import com.cb.cbcommon.data.constant.AppConstants.KEY_REPLY
import com.cb.cbcommon.data.constant.AppConstants.REPLY_ACTION
import com.cb.cbcommon.presentation.activity.MainActivity
import com.cb.cbcommon.receiver.CBBroadcastReceiver
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
            notificationChannelId = NotificationHelper.REPLY_CHANNEL_ID,
            smallIcon = smallIcon,
            largeIcon = largeIcon,
            replyAction = getReplyAction()
        )
    }

    private fun getReplyAction(): NotificationCompat.Action {
        // Create a PendingIntent for the reply action
        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, CBBroadcastReceiver::class.java).apply {
                action = REPLY_ACTION
            },
            PendingIntent.FLAG_MUTABLE
        )

        // Create RemoteInput for the direct reply
        val remoteInput = RemoteInput.Builder(KEY_REPLY)
            .setLabel("Type your reply...")
            .build()

        // Build and return the NotificationCompat.Action
        return NotificationCompat.Action.Builder(
            android.R.drawable.ic_menu_send,
            "Reply",
            replyPendingIntent
        ).addRemoteInput(remoteInput)
            .build()
    }


}