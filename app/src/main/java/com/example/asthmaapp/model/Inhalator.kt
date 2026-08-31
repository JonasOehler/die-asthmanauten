package com.example.asthmaapp.model

import kotlinx.serialization.Serializable


@Serializable
data class Inhalator(
    val id: String,
    val name: String,
    val description: List<String>,
    val videoUrl: String,
    val videoResId: Int = 0,
    val thumbnailUrl: String,
    val intakeTime: String,

)
