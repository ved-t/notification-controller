package com.example.notificationcontroller.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.notificationcontroller.MainActivity
import com.example.notificationcontroller.core.utils.openNotificationAccessSettings

@Composable
fun HomeScreen(
    onRequestNotificationPermission: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PermissionScreen(onRequestNotificationPermission = onRequestNotificationPermission)
    }
}

@Composable
fun PermissionScreen(
    onRequestNotificationPermission: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val permissionRequester = onRequestNotificationPermission
        ?: (context as? MainActivity)?.let { activity ->
            { activity.requestNotificationPermission() }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = {
            openNotificationAccessSettings(context)
        }) {
            Text("Enable Notification Access")
        }

        Button(onClick = {
            permissionRequester?.invoke()
        }) {
            Text("Request Notification Permission")
        }
    }
}
