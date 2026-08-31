package com.example.asthmaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asthmaapp.model.Todo
import com.example.asthmaapp.model.ChecklistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoFormScreen(
    navController: NavController,
    checklistViewModel: ChecklistViewModel,
    editTodoId: String? = null
) {
    val editTodo = checklistViewModel.todos.find { it.id == editTodoId }

    var title by remember { mutableStateOf(editTodo?.title ?: "") }
    var description by remember { mutableStateOf(editTodo?.description ?: "") }
    var time by remember { mutableStateOf(editTodo?.time ?: "") }
    var repeat by remember { mutableStateOf(editTodo?.repeat ?: "Täglich") }
    var doseEnabled by remember { mutableStateOf(editTodo?.dosePerInhalation != null) }
    var dosePerInhalation by remember {
        mutableStateOf(editTodo?.dosePerInhalation?.toString() ?: "")
    }
    var doseUnit by remember { mutableStateOf(editTodo?.doseUnit ?: "Mikrogramm (µg)") }
    var totalDose by remember { mutableStateOf(editTodo?.totalDose?.toString() ?: "") }
    var notification by remember { mutableStateOf(editTodo?.notification ?: false) }
    var emailNotification by remember { mutableStateOf(editTodo?.emailNotification ?: false) }
    var pushNotification by remember { mutableStateOf(editTodo?.pushNotification ?: false) }
    var reminderMinutesBefore by remember {
        mutableStateOf(editTodo?.reminderMinutesBefore?.toString() ?: "5")
    }

    val repeatOptions = listOf("Täglich", "Wöchentlich", "Monatlich")
    val doseUnitOptions = listOf("Mikrogramm (µg)", "Milligramm (mg)")

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editTodo != null) "To-do bearbeiten" else "Neues To-do") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Zurück",
                            tint = colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val todo = Todo(
                                id = editTodo?.id ?: System.currentTimeMillis().toString(),
                                title = title,
                                description = description,
                                time = time,
                                repeat = repeat,
                                dosePerInhalation = if (doseEnabled) dosePerInhalation.toIntOrNull() else null,
                                doseUnit = if (doseEnabled) doseUnit else null,
                                totalDose = if (doseEnabled) totalDose.toIntOrNull() else null,
                                notification = notification,
                                emailNotification = emailNotification,
                                pushNotification = pushNotification,
                                reminderMinutesBefore = if (notification) reminderMinutesBefore.toIntOrNull() else null,
                                done = editTodo?.done ?: false
                            )
                            if (editTodo != null) {
                                checklistViewModel.updateTodo(todo)
                            } else {
                                checklistViewModel.addTodo(todo)
                            }
                            navController.popBackStack()
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Text("Speichern")
                    }
                }
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("To-do (z.B. Name des Medikaments)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Beschreibung des To-dos (z.B. Inhalation)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Uhrzeit (z.B. 10:00)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Wiederholung:", modifier = Modifier.width(110.dp), color = colorScheme.onSurface)
                Spacer(Modifier.width(8.dp))
                DropdownMenuBox(
                    options = repeatOptions,
                    selectedOption = repeat,
                    onOptionSelected = { repeat = it }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Dosis", modifier = Modifier.weight(1f), color = colorScheme.onSurface)
                Switch(checked = doseEnabled, onCheckedChange = { doseEnabled = it })
            }

            if (doseEnabled) {
                Text("Dosis pro Inhalation", color = colorScheme.onSurfaceVariant)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = dosePerInhalation,
                        onValueChange = { dosePerInhalation = it },
                        label = { Text("Dose") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    DropdownMenuBox(
                        options = doseUnitOptions,
                        selectedOption = doseUnit,
                        onOptionSelected = { doseUnit = it }
                    )
                }

                Text("Dosis insgesamt", color = colorScheme.onSurfaceVariant)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = totalDose,
                        onValueChange = { totalDose = it },
                        label = { Text("Dose") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    DropdownMenuBox(
                        options = doseUnitOptions,
                        selectedOption = doseUnit,
                        onOptionSelected = { doseUnit = it }
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Benachrichtigung aktivieren", modifier = Modifier.weight(1f), color = colorScheme.onSurface)
                Switch(checked = notification, onCheckedChange = { notification = it })
            }

            if (notification) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("E-Mail-Benachrichtigung", modifier = Modifier.weight(1f), color = colorScheme.onSurface)
                    Switch(checked = emailNotification, onCheckedChange = { emailNotification = it })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Push-Benachrichtigung", modifier = Modifier.weight(1f), color = colorScheme.onSurface)
                    Switch(checked = pushNotification, onCheckedChange = { pushNotification = it })
                }
                OutlinedTextField(
                    value = reminderMinutesBefore,
                    onValueChange = { reminderMinutesBefore = it },
                    label = { Text("Minuten vorher (z.B. 5)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            if (editTodo != null) {
                Button(
                    onClick = {
                        checklistViewModel.deleteTodo(editTodo.id)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.error,
                        contentColor = colorScheme.onError
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text("Löschen")
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}