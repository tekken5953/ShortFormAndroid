package com.example.shortformandroid.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class RequestPermissionClass(private val context: Context) {
    private val permissionStorage = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun requestStorage() {
        kotlin.runCatching {
            requestPermissions(
                context as Activity,
                permissionStorage,
                0x000001
            )
        }.exceptionOrNull()?.stackTraceToString()
    }

    fun isLocationPermitted(): Boolean {
        for (perm in permissionStorage) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED)
                return false
        }

        return true
    }
}