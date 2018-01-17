package com.dmallcott.scamio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat


class LocalNotificationManager(private val context: Context, private val notificationManager: NotificationManager) {

    private val channelId = "scam.io"
    private val channelTitle = "scam.io"

    private val incomingCallNotificationId = 100001

    fun notifyIncomingCall() {
        val mBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Incoming call is baaaaad")
                .setContentText("Dayum, it be a hacker yo")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attachNotificationChannel()
        }

        notificationManager.notify(incomingCallNotificationId, mBuilder.build())
    }

    fun hideIncomingCall() {
        notificationManager.cancel(incomingCallNotificationId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun attachNotificationChannel() {
        val channel = NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }
}