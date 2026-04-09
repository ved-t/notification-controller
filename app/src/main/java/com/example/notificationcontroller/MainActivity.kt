package com.example.notificationcontroller

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.notificationcontroller.presentation.screens.home.HomeScreen
import com.example.notificationcontroller.presentation.screens.vibration.VibrationScreen
import com.example.notificationcontroller.presentation.screens.vibration.VibrationState
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
                var state by remember { mutableStateOf(VibrationState()) }
                VibrationScreen(
                    state = state,
                    onLevelSelected = { newLevel ->
                        state = state.copy(selectedLevel = newLevel)
                    },
                    onVibrateClick = {
                        triggerVibration(this, state.selectedLevel.duration)
                    }
                )
            }
        }
    }

    private fun triggerVibration(context: Context, duration: Long) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        val amplitude = 255 // 1 - 255

        val timings = longArrayOf(0, 200, 100, 500) // Wait 0, Vibrate 200ms, Wait 100ms, Vibrate 500ms
        val amplitudes = intArrayOf(0, 100, 0, 255) // Match intensities to the timings above

        val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
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
