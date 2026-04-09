package com.example.notificationcontroller.presentation.screens.vibration

import com.example.notificationcontroller.domain.model.VibrationLevel

data class VibrationState(
    val selectedLevel: VibrationLevel = VibrationLevel.MEDIUM
)