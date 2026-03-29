package com.example.notificationcontroller.presentation.screens.home

import com.example.notificationcontroller.domain.model.NotificationRule

data class HomeState(
    val rules: List<NotificationRule> = emptyList()
)
