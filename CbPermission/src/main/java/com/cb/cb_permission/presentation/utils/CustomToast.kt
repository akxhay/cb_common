package com.cb.cb_permission.presentation.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.cb.cb_permission.R

object CustomToast {

    fun showToast(context: Activity, text: String) {
        val inflater: LayoutInflater = context.layoutInflater
        val toastLayout: View =
            inflater.inflate(R.layout.custom_toast, context.findViewById(R.id.CustomToast))
        val toast = Toast(context.applicationContext)
        val toastText: TextView = toastLayout.findViewById(R.id.ToastText)
        toastText.text = text
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(toastLayout)
        toast.show()
    }
}