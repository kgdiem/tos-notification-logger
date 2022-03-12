package com.devbydiem.toslogger

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListener : NotificationListenerService() {
    val sheetsService = SheetsService(applicationContext)

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName

        println(pkg)

        // TODO: Get the package name for think or swim
        if (pkg == "test") {
            val title = sbn.notification.extras.getString(Notification.EXTRA_TITLE)
            val longTitle = sbn.notification.extras.getString(Notification.EXTRA_TITLE_BIG)

            val text = sbn.notification.extras.getString(Notification.EXTRA_TEXT)
            val longText = sbn.notification.extras.getString(Notification.EXTRA_TEXT_LINES)

            sheetsService.writeNotification(title, longTitle, text, longText)
        }
    }
}