package com.example.notificationcontroller.core.utils


import android.content.Context
import android.content.Intent
import android.provider.Settings

fun openNotificationAccessSettings(context: Context) {
    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    context.startActivity(intent)
}