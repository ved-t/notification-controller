package com.example.notificationcontroller.domain.usecase

import com.example.notificationcontroller.domain.model.NotificationRule
import com.example.notificationcontroller.domain.repository.RuleRepository

class SaveRuleUseCase(
    private val repository: RuleRepository
) {
    suspend operator fun invoke(rule: NotificationRule) {
        repository.saveRule(rule)
    }
}
