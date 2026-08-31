package com.example.asthmaapp.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MedicationViewModel(
    context: Context
) : ViewModel() {
    private val inhalatorRepo = InhalatorRepository(context)
    private val medicationRepo = MedicationRepository(context)

    var inhalators = mutableStateListOf<Inhalator>()
        private set

    var medications = mutableStateListOf<Medication>()
        private set

    fun addInhalatorById(id: String): Boolean {
        val inhalator = inhalatorRepo.getById(id)
        if (inhalator != null && inhalators.none { it.id == id }) {
            inhalators.add(inhalator)
            return true
        }
        return false
    }

    fun addMedicationById(id: String): Boolean {
        val medication = medicationRepo.getById(id)
        if (medication != null && medications.none { it.id == id }) {
            medications.add(medication)
            return true
        }
        return false
    }

    fun removeMedication(med: Medication) {
        medications.remove(med)
    }

    fun removeInhalator(inh: Inhalator) {
        inhalators.remove(inh)
    }
}
