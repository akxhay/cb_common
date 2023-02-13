package com.cb.cbtools.dynamic.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.factory.PermissionHandlerFactory

object ActionResolver {
    val TAG = "ActionResolver"

    fun getAction(appName: String, context: Activity, args: Array<String>?): () -> Unit {
        try {
            args?.let {
                Log.d(TAG, "getAction: ${args.asList()}")
                if (args.isNotEmpty()) {
                    return when (args[0]) {
                        "TOAST" -> {
                            {
                                makeToastAction(context = context, text = args[1])
                            }
                        }
                        "URL" -> {
                            {
                                openExternalUrl(context = context, url = args[1])
                            }
                        }
                        "SHARE" -> {
                            {
                                openShareIntentAction(
                                    context = context,
                                    title = args[1],
                                    subject = args[2],
                                    text = args[3]
                                )
                            }
                        }
                        "PERMISSION" -> {
                            {
                                permissionAction(
                                    appName = appName,
                                    context = context,
                                    currentPermission = args[1]
                                )
                            }
                        }

                        else -> {
                            {}
                        }
                    }
                }

            }

        } catch (e: Exception) {
            Log.d("ExceptionHelper", e.toString())

            return {
                makeToastAction(context = context, text = "incorrect parameters")
            }
        }

        return {}
    }

    private fun permissionAction(
        appName: String,
        context: Activity,
        currentPermission: String
    ) {
        val type = PermissionType.valueOf(
            currentPermission
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHandlerFactory.getHandlerForPermission(
                type
            )?.let {
                if (it.isPermitted(context)) {
                    Toast.makeText(
                        context,
                        "Already permitted",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Please Enable ${type.type} for $appName",
                        Toast.LENGTH_LONG
                    ).show()
                    it.askPermission(context)
                }
            }
        }

    }


    private fun makeToastAction(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


    private fun openShareIntentAction(
        context: Context,
        title: String,
        subject: String,
        text: String
    ) {
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, subject)
            i.putExtra(Intent.EXTRA_TEXT, text)
            context.startActivity(
                Intent.createChooser(
                    i,
                    title
                )
            )
        } catch (e: Exception) {
            Log.d("ExceptionHelper", e.toString())

        }
    }


    private fun openExternalUrl(context: Context, url: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.d("ExceptionHelper", e.toString())

        }
    }

}