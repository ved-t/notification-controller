package com.example.notificationcontroller.data.datasource.local

import com.example.notificationcontroller.domain.model.NotificationRule

interface RuleLocalDataSource {
    suspend fun getRuleForApp(packageName: String): NotificationRule?
    suspend fun saveRule(rule: NotificationRule)
}
