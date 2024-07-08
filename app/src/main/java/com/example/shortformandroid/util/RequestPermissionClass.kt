package com.example.shortformandroid.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class RequestPermissionClass(private val activity: Activity) {
    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1
        const val PERMISSION_CODE_READ_EXTERNAL_STORAGE = 2
    }


    private val permissionStorage = arrayOf(
        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) android.Manifest.permission.READ_MEDIA_IMAGES
        else android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun requestStorage() {
        showPermissionAlertDialog()
    }

    fun isStoragePermissionGranted(): Boolean {
        for (perm in permissionStorage) {
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED)
                return false
        }

        return true
    }

    private fun showPermissionAlertDialog() {
        AlertDialog.Builder(activity)
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("사진을 선택 하시려면 권한이 필요합니다.")
            .setPositiveButton("허용하기") { _, _ ->
                requestPermissions(
                    activity,
                    permissionStorage,
                    PERMISSION_CODE_READ_EXTERNAL_STORAGE
                )
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }
}