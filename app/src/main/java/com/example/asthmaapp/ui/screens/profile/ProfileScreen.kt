package com.example.asthmaapp.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asthmaapp.R
import com.example.asthmaapp.model.ProfileData
import com.google.gson.Gson
import java.io.File

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    var profileData by remember { mutableStateOf<ProfileData?>(null) }

    LaunchedEffect(Unit) {
        copyAssetToInternalStorageIfNeeded(context, "profile_data.json")
        profileData = loadProfileDataFromInternalStorage(context)
    }

    if (profileData == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        ProfileContent(profileData!!, context, navController) { updatedProfile ->
            profileData = updatedProfile
        }
    }
}

@Composable
fun ProfileContent(
    data: ProfileData,
    context: Context,
    navController: NavController,
    onProfileUpdated: (ProfileData) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    var fullName by remember { mutableStateOf(data.fullName) }
    var email by remember { mutableStateOf(data.email) }
    var birthDate by remember { mutableStateOf(data.birthDate) }
    var phoneNumber by remember { mutableStateOf(data.phoneNumber) }
    var asthmaType by remember { mutableStateOf(data.asthmaType) }
    var actScore by remember { mutableStateOf(data.actScore) }
    var peakFlow by remember { mutableStateOf(data.peakFlow) }
    var inhaler by remember { mutableStateOf(data.inhaler) }
    var reminders by remember { mutableStateOf(data.reminders) }

    val scrollState = rememberScrollState()
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background) // ✅ Dark-Mode-kompatibel
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Zurück",
                    tint = colorScheme.onSurface // ✅ Theme-Farbe verwenden
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.placeholder_profile),
            contentDescription = "Profilbild",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isEditing) {
            EditableField("Name", fullName) { fullName = it }
            EditableField("E-Mail", email) { email = it }
            EditableField("Geburtsdatum", birthDate) { birthDate = it }
            EditableField("Telefonnummer", phoneNumber) { phoneNumber = it }
        } else {
            Text(fullName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = colorScheme.onBackground)
            Text(email, style = MaterialTheme.typography.bodyMedium, color = colorScheme.onSurfaceVariant)
            Text(birthDate, style = MaterialTheme.typography.bodyMedium, color = colorScheme.onSurfaceVariant)
            Text(phoneNumber, style = MaterialTheme.typography.bodyMedium, color = colorScheme.onSurfaceVariant)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Gesundheitsdaten",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start),
            fontWeight = FontWeight.SemiBold,
            color = colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isEditing) {
                    EditableField("Asthma-Typ", asthmaType) { asthmaType = it }
                    EditableField("ACT-Wert", actScore) { actScore = it }
                    EditableField("Peak-Flow", peakFlow) { peakFlow = it }
                    EditableField("Inhalator", inhaler) { inhaler = it }
                    EditableField("Erinnerungen", reminders) { reminders = it }
                } else {
                    ProfileInfoRow("Asthma-Typ", asthmaType)
                    ProfileInfoRow("Letzter ACT-Wert", actScore)
                    ProfileInfoRow("Letzter Peak-Flow", peakFlow)
                    ProfileInfoRow("Bevorzugter Inhalator", inhaler)
                    ProfileInfoRow("Erinnerungen", reminders)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            if (isEditing) {
                Button(onClick = {
                    val updatedProfile = ProfileData(
                        fullName, email, birthDate, phoneNumber,
                        asthmaType, actScore, peakFlow, inhaler, reminders
                    )
                    saveProfileDataToInternalStorage(context, updatedProfile)
                    onProfileUpdated(updatedProfile)
                    isEditing = false
                }) {
                    Text("Speichern")
                }

                OutlinedButton(onClick = {
                    fullName = data.fullName
                    email = data.email
                    birthDate = data.birthDate
                    phoneNumber = data.phoneNumber
                    asthmaType = data.asthmaType
                    actScore = data.actScore
                    peakFlow = data.peakFlow
                    inhaler = data.inhaler
                    reminders = data.reminders
                    isEditing = false
                }) {
                    Text("Abbrechen")
                }
            } else {
                Button(onClick = { isEditing = true }) {
                    Text("Bearbeiten")
                }

                OutlinedButton(onClick = { /* TODO: Logout */ }) {
                    Text("Abmelden")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = colorScheme.onSurfaceVariant
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurfaceVariant,
                cursorColor = colorScheme.primary
            )
        )
    }
}

// Dateioperationen
fun copyAssetToInternalStorageIfNeeded(context: Context, fileName: String) {
    val file = File(context.filesDir, fileName)
    if (!file.exists()) {
        val assetJson = context.assets.open(fileName).bufferedReader().use { it.readText() }
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(assetJson.toByteArray())
        }
    }
}

fun loadProfileDataFromInternalStorage(context: Context): ProfileData {
    val file = File(context.filesDir, "profile_data.json")
    val json = file.bufferedReader().use { it.readText() }
    return Gson().fromJson(json, ProfileData::class.java)
}

fun saveProfileDataToInternalStorage(context: Context, profile: ProfileData) {
    val jsonString = Gson().toJson(profile)
    context.openFileOutput("profile_data.json", Context.MODE_PRIVATE).use {
        it.write(jsonString.toByteArray())
    }
}
