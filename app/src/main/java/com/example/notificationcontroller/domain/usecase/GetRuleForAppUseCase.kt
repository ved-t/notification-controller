package com.example.notificationcontroller.domain.usecase

import com.example.notificationcontroller.domain.model.NotificationRule
import com.example.notificationcontroller.domain.repository.RuleRepository

class GetRuleForAppUseCase(
    private val repository: RuleRepository
) {
    suspend operator fun invoke(packageName: String): NotificationRule? {
        return repository.getRuleForApp(packageName)
    }
}
