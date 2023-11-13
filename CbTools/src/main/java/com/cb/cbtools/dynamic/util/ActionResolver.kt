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
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.functions

object ActionResolver {
    val tag = "ActionResolver"

    fun getAction(appName: String, context: Activity, args: Array<String>?): () -> Unit {
        try {
            args?.let {
                Log.d(tag, "getAction: ${args.asList()}")
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

                        "REFLECTION" -> {
                            {
                                reflect(
                                    context = context,
                                    args = args
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

    private fun reflect(context: Activity, args: Array<String>) {
        val methodType = args[1]
        val className = args[2]
        val methodName = args[3]
        val clazz = Class.forName(className)
        val classList = ArrayList<Class<*>>()
        val valueList = ArrayList<String>()
        for (i in 4..args.size.dec()) {
            classList.add(String::class.java)
            valueList.add(args[i])
        }
        if (methodType == "STATIC") {
            val companion = clazz.kotlin.companionObject
            companion?.functions?.first { it.name == methodName }
                ?.call(companion.objectInstance, context, *valueList.toTypedArray())

        } else {
            val method = clazz
                .getMethod(methodName, Context::class.java, *classList.toTypedArray())
            val ins = clazz.getConstructor().newInstance()
            method.invoke(ins, context, *valueList.toTypedArray())
        }


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