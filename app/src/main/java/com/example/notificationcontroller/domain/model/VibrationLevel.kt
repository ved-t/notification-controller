package com.example.notificationcontroller.domain.model

enum class VibrationLevel(theName: String, val duration: Long) {
    LOW("Low", 100L),
    MEDIUM("Medium", 500L),
    HIGH("High", 1000L);

    override fun toString(): String = name.lowercase().replaceFirstChar { it.uppercase() }
}