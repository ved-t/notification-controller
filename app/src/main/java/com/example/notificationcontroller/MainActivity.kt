package com.example.notificationcontroller

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.notificationcontroller.presentation.screens.home.HomeScreen
import com.example.notificationcontroller.ui.theme.NotificationControllerTheme

class MainActivity : ComponentActivity() {
    private val notificationPermissionRequestCode = 101
    private val maxPermissionDeclineCount = 3
    private var permissionDeclineCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationControllerTheme {
                HomeScreen(
                    onRequestNotificationPermission = { requestNotificationPermission() }
                )
            }
        }
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            notificationPermissionRequestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != notificationPermissionRequestCode) {
            return
        }

        val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (granted) {
            permissionDeclineCount = 0
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            return
        }

        permissionDeclineCount += 1

        if (permissionDeclineCount < maxPermissionDeclineCount) {
            requestNotificationPermission()
        } else {
            Toast.makeText(this, "Notification permission denied too many times. Closing app.", Toast.LENGTH_LONG).show()
            finishAffinity()
        }
    }
}
