package com.devbydiem.toslogger

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionService {
    val requiredPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECEIVE_BOOT_COMPLETED
    )

    fun checkPermissions(context: Context): Array<String> {
        val permissionsRequired = ArrayList<String>(0)

        for (permission in requiredPermissions) {
            val isAllowed = ContextCompat.checkSelfPermission(context, permission)

            if(isAllowed == PackageManager.PERMISSION_DENIED) {
                permissionsRequired.add(permission)
            }
        }

        return permissionsRequired.toTypedArray()
    }
}