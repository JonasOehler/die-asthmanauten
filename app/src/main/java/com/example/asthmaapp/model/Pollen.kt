package com.example.asthmaapp.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Pollen(
    val nameRes: Int,          // string resource ID, like R.string.ragweed
    val count: PollenCount,
    var isVisible: MutableState<Boolean> = mutableStateOf(true)// toggled by chip
)
