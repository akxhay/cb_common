package com.cb.cb_permission.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import com.cb.cb_permission.R
import com.cb.cb_permission.listeners.GenericOnClickListener


object AlertHelper {

    fun showMessageAlertWithAction(
        context: Activity,
        text: String?,
        title: String,
        genericOnClickListener: GenericOnClickListener,
        confirmButtonText: String = "Confirm"
    ) {
        try {
            val builder = AlertDialog.Builder(context)
            val inflater = context.layoutInflater
            val content =
                inflater.inflate(R.layout.content_custom_dialog_message, null)
            val textSnack: TextView = content.findViewById(R.id.text_snack)
            val textSnackLayout: LinearLayout = content.findViewById(R.id.text_snack_layout)
            if (text != null) {
                textSnackLayout.visibility = VISIBLE
                textSnack.text = text
            } else {
                textSnackLayout.visibility = GONE
            }
            builder.setView(content).setTitle(title)
                .setPositiveButton(confirmButtonText) { dialog, _ ->
                    genericOnClickListener.onCLick()
                    dialog.dismiss()
                }.setNegativeButton(
                    "Close"
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.show()

        } catch (e: Exception) {
            println(e)

        }
    }


}