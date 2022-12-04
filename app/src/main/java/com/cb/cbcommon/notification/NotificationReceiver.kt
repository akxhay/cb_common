package com.cb.cbcommon.notification

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationReceiver : NotificationListenerService() {


    override fun onNotificationPosted(statusBarNotification: StatusBarNotification) {
        super.onNotificationPosted(statusBarNotification)

    }

    override fun onNotificationRemoved(statusBarNotification: StatusBarNotification) {
        super.onNotificationRemoved(statusBarNotification)

    }

    //This will be called for each intent launch, even if the service is already started and is reused
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }


}