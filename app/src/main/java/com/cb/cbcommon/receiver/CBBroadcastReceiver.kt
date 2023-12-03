package com.cb.cbcommon.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.RemoteInput
import com.cb.cbcommon.data.constant.AppConstants.KEY_REPLY
import com.cb.cbcommon.data.constant.AppConstants.REPLY_ACTION


class CBBroadcastReceiver : BroadcastReceiver() {
    private val tag = "CBBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.e(tag, "Broadcast event: $action")
        if (action == REPLY_ACTION) {
            handleReply(context, intent)
            val clearIntent = Intent(intent.action)
            intent.extras?.let { clearIntent.putExtras(it) }
        }
    }

    private fun handleReply(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence(KEY_REPLY).toString()
        Log.d(tag, "Received reply: $replyText")
        Toast.makeText(context, "Received reply: $replyText", Toast.LENGTH_SHORT).show()

        // Handle the received reply as needed
    }
}
