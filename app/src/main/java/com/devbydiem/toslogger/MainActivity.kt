package com.devbydiem.toslogger

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

class MainActivity : AppCompatActivity() {
    val sheetsService = SheetsService()
    val requiredPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }

    fun exportSheet(view: View) {
        val sheet = sheetsService.openSheet(filesDir)

        val uri = FileProvider.getUriForFile(this, packageName, sheet)

        ShareCompat.IntentBuilder(this)
            .setStream(uri)
            .setType("application/csv")
            .getIntent()
            .setAction(Intent.CATEGORY_APP_EMAIL)
            .setDataAndType(uri, "application/csv")
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    fun checkPermissions() {
        val permissionsRequired = ArrayList<String>(0)

        for (permission in requiredPermissions) {
            val isAllowed = ContextCompat.checkSelfPermission(this, permission)

            if(isAllowed == PackageManager.PERMISSION_DENIED) {
                permissionsRequired.add(permission)
            }
        }

        if(permissionsRequired.count() > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionsRequired.toTypedArray(), 9)
        }
    }
}