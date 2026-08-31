package com.example.asthmaapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.asthmaapp.R
import com.example.asthmaapp.model.MedicationViewModel

@Composable
fun MedicationListScreen(
    navController: NavController,
    viewModel: MedicationViewModel
) {
    val inhalators = viewModel.inhalators
    val medications = viewModel.medications

    var inhalatorEditMode by rememberSaveable { mutableStateOf(false) }
    var medicationEditMode by rememberSaveable { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // Inhalatoren
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.inhaler_list_title), style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { inhalatorEditMode = !inhalatorEditMode }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
            }
        }

        items(inhalators, key = { it.id }) { inhalator ->
            ListItem(
                modifier = Modifier
                    .clickable(enabled = !inhalatorEditMode) {
                        navController.navigate("inhalator_detail/${inhalator.id}")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                leadingContent = {
                    AsyncImage(
                        model = inhalator.thumbnailUrl,
                        contentDescription = inhalator.name,
                        modifier = Modifier.size(80.dp).padding(end = 16.dp)
                    )
                },
                headlineContent = { Text(inhalator.name, style = MaterialTheme.typography.titleMedium) },
                supportingContent = { Text(inhalator.intakeTime, style = MaterialTheme.typography.bodySmall) },
                trailingContent = {
                    if (inhalatorEditMode) {
                        IconButton(onClick = { viewModel.removeInhalator(inhalator) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Löschen")
                        }
                    }
                }
            )
            HorizontalDivider()
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { navController.navigate("add_item/inhalator") }
            ) {
                Text(text = stringResource(id = R.string.add_inhaler))
            }
        }


        item { Spacer(modifier = Modifier.height(32.dp)) }

        // Medikamente
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.medication_title), style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { medicationEditMode = !medicationEditMode }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
            }
        }

        items(medications, key = { it.id }) { medication ->
            ListItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp), // Kein Klickverhalten
                leadingContent = {
                    AsyncImage(
                        model = medication.thumbnailUrl,
                        contentDescription = medication.name,
                        modifier = Modifier.size(80.dp).padding(end = 16.dp)
                    )
                },
                headlineContent = {
                    Column {
                        Text(medication.name, style = MaterialTheme.typography.titleMedium)
                        Text(medication.dosageForm, style = MaterialTheme.typography.bodySmall)
                    }
                },
                supportingContent = { Text(medication.intakeTime, style = MaterialTheme.typography.bodySmall) },
                trailingContent = {
                    if (medicationEditMode) {
                        IconButton(onClick = { viewModel.removeMedication(medication) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Löschen")
                        }
                    }
                }
            )
            HorizontalDivider()
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { navController.navigate("add_item/medication") }
            ) {
                Text(text = stringResource(id = R.string.add_medication))
            }
        }
    }
}
