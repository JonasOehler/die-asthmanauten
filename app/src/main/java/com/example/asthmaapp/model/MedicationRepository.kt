package com.example.asthmaapp.model

import android.content.Context
import kotlinx.serialization.json.Json
import java.util.Locale

class MedicationRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    private val medication: List<Medication> by lazy {
        val locale: Locale = context.resources.configuration.locales[0]
        val languageCode = locale.language

        val fileName = when (languageCode) {
            "en" -> "medication_en.json"
            "de" -> "medication_de.json"
            else -> "medication_de.json" // Fallback
        }

        val rawJson = context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }

        json.decodeFromString(rawJson)
    }

    fun getAll(): List<Medication> = medication

    fun getById(id: String): Medication? = medication.firstOrNull { it.id == id }
}
