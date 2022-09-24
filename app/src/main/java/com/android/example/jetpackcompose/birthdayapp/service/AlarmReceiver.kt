package com.android.example.jetpackcompose.birthdayapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.example.jetpackcompose.birthdayapp.MainActivity
import com.android.example.jetpackcompose.birthdayapp.R

/**
 * Created by Alvina Lushnikova on 23.09.22.
 */

const val notificationId = 1
const val notificationChannel = "NotificationChannel1"
const val titleExtra = "TitleExtra"
const val messageExtra = "MessageExtra"

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        p1?.let {
            handleAlaramData(context, it)
        }
    }

    private fun handleAlaramData(context: Context?, intent: Intent) {

        context?.let {

            val title = intent.getStringExtra(titleExtra)
            val description = intent.getStringExtra(messageExtra)

            createNotificationChannel(context = it)

            createNotification(
                context = it,
                title = title ?: "title",
                description = description ?:"description",
            )

        }

    }

    private fun createNotification(
        context: Context,
        title : String,
        description : String,
    ) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_main)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", importance).apply {
                description = "CHANNEL_DESP"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

}