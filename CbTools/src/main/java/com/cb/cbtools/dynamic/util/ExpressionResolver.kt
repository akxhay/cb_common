package com.cb.cbtools.dynamic.util

import android.content.SharedPreferences
import android.util.Log
import com.cb.cbtools.dynamic.data.Expression

object ExpressionResolver {
    val TAG = "ExpressionResolver"

    fun evaluate(preferences: SharedPreferences, showExpression: Expression?): Boolean {
        if (showExpression == null) return true
        Log.i(TAG, "evaluate ${showExpression.key} : ${showExpression.value}")
        return if (showExpression.operator == "ne")
            preferences.getString(showExpression.key, null) != showExpression.value
        else preferences.getString(showExpression.key, null) == showExpression.value

    }


}