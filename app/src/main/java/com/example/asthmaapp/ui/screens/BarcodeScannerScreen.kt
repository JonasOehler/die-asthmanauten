package com.example.asthmaapp.ui.screens

import CameraOverlay
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.asthmaapp.R
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.compose.ui.res.stringResource
import com.example.asthmaapp.ui.components.BarcodeScannerView
import com.example.asthmaapp.model.BarcodeViewModel
import com.example.asthmaapp.model.MedicationViewModel
import com.example.asthmaapp.model.NavBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeScannerScreen(
    navController: NavController,
    itemType: String,
    medicationViewModel: MedicationViewModel = viewModel(),
    barcodeViewModel: BarcodeViewModel = viewModel()
) {
    val context = LocalContext.current
    val barcode = barcodeViewModel.scannedCode.value
    var camera by remember { mutableStateOf<Camera?>(null) }
    var isTorchOn by remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(false) }
    var scannedOnce by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputImage = InputImage.fromFilePath(context, it)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { code ->
                        if (!scannedOnce) {
                            barcodeViewModel.onCodeScanned(code)
                        }
                    }
                }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    LaunchedEffect(barcode) {
        if (barcode != null && !scannedOnce) {
            scannedOnce = true
            val wasAdded = when (itemType.lowercase()) {
                "inhalator" -> medicationViewModel.addInhalatorById(barcode)
                "medication" -> medicationViewModel.addMedicationById(barcode)
                else -> false
            }
            if (wasAdded) {
                barcodeViewModel.onCodeScanned(null)
                navController.navigate(NavBarItem.Medication.route) {
                    popUpTo(NavBarItem.Medication.route) { inclusive = true }
                }
            } else {
                scannedOnce = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.scan_barcode_title))},
                actions = {
                    IconButton(onClick = {
                        isTorchOn = !isTorchOn
                        camera?.cameraControl?.enableTorch(isTorchOn)
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (isTorchOn) R.drawable.outline_bolt_24dp else R.drawable.baseline_bolt_24dp),
                            contentDescription = stringResource(id = R.string.flash_button_description)
                        )
                    }

                    IconButton(onClick = {
                        camera?.cameraControl?.enableTorch(false)
                        isTorchOn = false
                        imagePickerLauncher.launch("image/*")
                    }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_panorama_24dp),
                            contentDescription = stringResource(id = R.string.image_button_description)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.outline_arrow_back_24dp),
                            contentDescription = stringResource(id = R.string.back_button_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (hasPermission) {
                BarcodeScannerView(
                    onBarcodeScanned = { code ->
                        if (!scannedOnce) {
                            barcodeViewModel.onCodeScanned(code)
                        }
                    },
                    onCameraReady = { camera = it }
                )
                CameraOverlay()
            } else {
                Text(stringResource(id = R.string.camera_permission_text), modifier = Modifier.padding(16.dp))
            }
        }
    }
}
