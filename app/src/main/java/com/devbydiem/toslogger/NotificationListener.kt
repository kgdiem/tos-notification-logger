package com.devbydiem.toslogger

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class NotificationListener : NotificationListenerService() {
    val sheetsService = SheetsService()
    private val tosPackageName = "com.devexperts.tdmobile.platform.android.thinkorswim"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName

        if (pkg == tosPackageName) {
            val title = sbn.notification.extras.getString(Notification.EXTRA_TITLE)
            val longTitle = sbn.notification.extras.getString(Notification.EXTRA_TITLE_BIG)

            val text = sbn.notification.extras.getString(Notification.EXTRA_TEXT)
            val longText = sbn.notification.extras.getString(Notification.EXTRA_TEXT_LINES)

            sheetsService.writeNotification(filesDir, title, longTitle, text, longText)

            sendNotification(pkg, null)
        }

    }

    private fun sendNotification(pkg: String, message: String?) {
        val CHANNEL_ID = getString(R.string.notification_channel_id)

        val content = message ?: "Notification captured from $pkg"

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Tos Logger")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}