package com.android.example.slicecodelab

import android.net.Uri
import androidx.annotation.Nullable
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.core.SliceHints.INFINITY
import android.app.PendingIntent
import android.content.Intent
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.builders.ListBuilder.LARGE_IMAGE
import androidx.slice.builders.SliceAction


class MySliceProvider : SliceProvider() {

    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    override fun onBindSlice(sliceUri: Uri): Slice? {
        when (sliceUri.getPath()) {
            "/temperature" -> return createTemperatureSlice(sliceUri)
        }
        return null
    }

    @Nullable
    private fun createTemperatureSlice(sliceUri: Uri): Slice? {

        val likeIcon = if (MainActivity.sLiked) R.drawable.ic_like else R.drawable.ic_unlike

        // Define the actions used in this slice
        val actionLike = SliceAction(
                getLikeIntent(MainActivity.sLiked),
                IconCompat.createWithResource(context, likeIcon),
                "Like Post")

        val reblogAction = SliceAction(
                getReblogIntent(true),
                IconCompat.createWithResource(context, R.drawable.ic_reblog),
                "Reblog Post")

        // The primary action; this will activate when the row is tapped
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val openTempActivity = SliceAction(pendingIntent,
                IconCompat.createWithResource(context, R.drawable.ic_tumblr_logo_blue), "Temperature controls")

        return ListBuilder(context, sliceUri, INFINITY)
                .setAccentColor(0xff35465c.toInt())
                .addAction(openTempActivity)
                .setHeader {
                    it.apply {
                        setTitle("fuckyeaheng")
                    }
                }
//                .addRow {
//                    it.apply {
//                        setTitle("Oh, Worm?")
////                        setPrimaryAction(openTempActivity)
//                    }
//                }
                .addGridRow {
                    it.apply {
                        addCell {
                            it.apply {
                                addImage(IconCompat.createWithResource(context, R.drawable.photo), LARGE_IMAGE)
                                setContentIntent(pendingIntent)
                            }
                        }
                    }
                }
                .addRow {
                    it.apply {
                        setTitle("12 Notes")
                        addEndItem(reblogAction)
                        addEndItem(actionLike)
                        setPrimaryAction(openTempActivity)
                    }
                }
                .build()
    }

    companion object {
        var REQ_CODE = 0
    }

    // This method constructs a PendingIntent to trigger the BroadcastReceiver.
    private fun getLikeIntent(value: Boolean): PendingIntent {
        val intent = Intent(MyBroadcastReceiver.ACTION_LIKE).apply {
            setClass(context, MyBroadcastReceiver::class.java)
            putExtra(MyBroadcastReceiver.EXTRA_LIKE, !value)
        }

        return PendingIntent.getBroadcast(context, REQ_CODE++, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    // This method constructs a PendingIntent to trigger the BroadcastReceiver.
    private fun getReblogIntent(value: Boolean): PendingIntent {
        val intent = Intent(MyBroadcastReceiver.ACTION_REBLOG).apply {
            setClass(context, MyBroadcastReceiver::class.java)
            putExtra(MyBroadcastReceiver.EXTRA_REBLOG, value)
        }

        return PendingIntent.getBroadcast(context, REQ_CODE++, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }
}