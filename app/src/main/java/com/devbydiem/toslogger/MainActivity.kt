package com.devbydiem.toslogger

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

class MainActivity : AppCompatActivity() {
    val sheetsService = SheetsService()
    val permissionService = PermissionService()

    private val ACTION_NOTIFICATION_LISTENER_SETTINGS =
        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val channelId = getString(R.string.notification_channel_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        checkPermissions()

        startService(Intent(this, NotificationListener::class.java))
    }

    override fun onDestroy() {
        val intent = Intent()
        intent.action = "loggerappkilled"
        intent.setClass(this, BootReceiver::class.java)

        sendBroadcast(intent)

        super.onDestroy()
    }

    fun enableNotificationListener(view: View) {
        startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    fun exportSheet(view: View) {
        val sheet = sheetsService.openSheet(filesDir)

        val uri = FileProvider.getUriForFile(this, packageName, sheet)

        val intentBuilder = ShareCompat.IntentBuilder(this)
            .setStream(uri)
            .setType("text/csv")
            .intent
            .setAction(Intent.ACTION_SEND)
            .setDataAndType(uri, "text/csv")
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intentBuilder)
    }

    fun checkPermissions() {
        val permissionsRequired = permissionService.checkPermissions(this)

        if(permissionsRequired.count() > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionsRequired, 9)
        }
    }
}