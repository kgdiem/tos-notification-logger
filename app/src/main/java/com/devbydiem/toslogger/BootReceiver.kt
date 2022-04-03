package com.devbydiem.toslogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    val permissionService = PermissionService()
    val intents = arrayOf("android.intent.action.BOOT_COMPLETED", "loggerappkilled")

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intents.contains(intent?.action)) {
            val requiredPermissions = permissionService.checkPermissions(context)

            // Only start service if the required permissions have been granted
            if(requiredPermissions.isEmpty()) {
                context.startService(Intent(context, NotificationListener::class.java))
            }
        }
    }
}