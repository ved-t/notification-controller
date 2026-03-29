package com.example.notificationcontroller.domain.repository

import com.example.notificationcontroller.domain.model.NotificationRule

interface RuleRepository {
    suspend fun getRuleForApp(packageName: String): NotificationRule?
    suspend fun saveRule(rule: NotificationRule)
}
