package com.example.asthmaapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BarcodeViewModel : ViewModel() {
    private val _scannedCode = mutableStateOf<String?>(null)
    val scannedCode: State<String?> = _scannedCode

    fun onCodeScanned(code: String?) {
        _scannedCode.value = code
    }
}