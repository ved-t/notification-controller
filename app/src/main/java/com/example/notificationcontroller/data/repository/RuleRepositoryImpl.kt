package com.example.notificationcontroller.data.repository

import com.example.notificationcontroller.data.datasource.local.RuleLocalDataSource
import com.example.notificationcontroller.domain.model.NotificationRule
import com.example.notificationcontroller.domain.repository.RuleRepository

class RuleRepositoryImpl(
    private val localDataSource: RuleLocalDataSource
) : RuleRepository {
    override suspend fun getRuleForApp(packageName: String): NotificationRule? {
        return localDataSource.getRuleForApp(packageName)
    }

    override suspend fun saveRule(rule: NotificationRule) {
        localDataSource.saveRule(rule)
    }
}
