package com.android.example.slicecodelab

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (ACTION_LIKE == action && intent.extras?.containsKey(EXTRA_LIKE) == true) {
            MainActivity.like(context, intent.extras.getBoolean(EXTRA_LIKE))
        } else if ( ACTION_REBLOG == action && intent.extras?.containsKey(EXTRA_REBLOG) == true) {
            MainActivity.reblog(context)
        }
    }

    companion object {
        var ACTION_LIKE = "com.android.example.slicecodelab.ACTION_LIKE"
        var EXTRA_LIKE = "com.android.example.slicecodelab.EXTRA_LIKE"
        var ACTION_REBLOG = "com.android.example.slicecodelab.ACTION_REBLOG"
        var EXTRA_REBLOG = "com.android.example.slicecodelab.EXTRA_LIKE"
    }
}