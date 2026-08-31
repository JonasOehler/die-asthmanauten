package com.example.asthmaapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Medication(
    val id: String,
    val name: String,
    val dosageForm: String,
    val thumbnailUrl: String,
    val intakeTime: String
)
