package com.example.notificationcontroller.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class AppNotificationListener : NotificationListenerService() {

    companion object {
        private const val TAG = "NotifListener"
        // Changed ID to force a channel refresh with new settings
        private const val SILENT_CHANNEL_ID = "silent_vibration_channel"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "🚀 Service Created")
        createSilentChannel()
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "✅ Notification Listener Connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        val packageName = sbn.packageName
        val notification = sbn.notification

        val title = notification.extras.getString("android.title")
        val text = notification.extras.getString("android.text")

        Log.d(TAG, "📩 Notification Received")
        Log.d(TAG, "App: $packageName")
        Log.d(TAG, "Title: $title")
        Log.d(TAG, "Text: $text")

        // 🔥 HARDCODED RULE (Phase 1)
        if (packageName == "com.whatsapp") {

            Log.d(TAG, "🚫 Applying silent rule to WhatsApp")

            // Cancel original notification
            cancelNotification(sbn.key)

            val newNotification = Notification.Builder(this, SILENT_CHANNEL_ID)
                .setContentTitle(title ?: "")
                .setContentText(text ?: "")
                .setSmallIcon(notification.smallIcon)
                .setAutoCancel(true)
                .build()

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Use unique ID to avoid overwrite issues
            val newId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

            manager.notify(newId, newNotification)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d(TAG, "❌ Notification Removed: ${sbn.packageName}")
    }

    // 🔥 REQUIRED for Android 8+
    private fun createSilentChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Changed importance to DEFAULT to support vibration
            val channel = NotificationChannel(
                SILENT_CHANNEL_ID,
                "Silent Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Used for muted notifications with vibration"
                setSound(null, null) // Keep it silent (no sound)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 200, 100, 200)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
