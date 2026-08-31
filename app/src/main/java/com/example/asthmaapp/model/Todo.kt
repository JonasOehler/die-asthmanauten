package com.example.asthmaapp.model
import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val repeat: String,
    val dosePerInhalation: Int?,
    val doseUnit: String?,
    val totalDose: Int?,
    val notification: Boolean,
    val emailNotification: Boolean,
    val pushNotification: Boolean,
    val reminderMinutesBefore: Int?,
    val done: Boolean
)
