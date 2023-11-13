package com.cb.cbcommon

import android.content.Context
import android.widget.Toast

class DynamicAppUtil {

    companion object {
        fun test(context: Context, name: String) {
            Toast.makeText(context, "Toast from DynamicAppUtil", Toast.LENGTH_SHORT).show()
        }

    }
}