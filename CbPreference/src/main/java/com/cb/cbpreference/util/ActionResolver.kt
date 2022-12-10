package com.cb.cbpreference.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

object ActionResolver {
    val TAG = "ActionResolver"

    fun getAction(context: Context, args: Array<String>?): () -> Unit {
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
            Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.d("ExceptionHelper", e.toString())

        }
    }

}