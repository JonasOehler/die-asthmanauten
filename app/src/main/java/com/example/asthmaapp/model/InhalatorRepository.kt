package com.example.asthmaapp.model

import android.content.Context
import kotlinx.serialization.json.Json
import java.util.Locale

class InhalatorRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    private fun getLocalizedAssetFileName(): String {
        val locale: Locale = context.resources.configuration.locales[0]
        return when (locale.language) {
            "en" -> "inhalers_en.json"
            "de" -> "inhalers_de.json"
            else -> "inhalers_de.json"
        }
    }

    private val inhalators: List<Inhalator> by lazy {
        val fileName = getLocalizedAssetFileName()
        val rawJson = context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val parsed = json.decodeFromString<List<Inhalator>>(rawJson)

        parsed.map { inhalator ->
            val resId = context.resources.getIdentifier(
                inhalator.videoUrl,
                "raw",
                context.packageName
            )
            inhalator.copy(videoResId = resId)
        }
    }


    fun getAll(): List<Inhalator> = inhalators

    fun getById(id: String): Inhalator? = inhalators.firstOrNull { it.id == id }
}
