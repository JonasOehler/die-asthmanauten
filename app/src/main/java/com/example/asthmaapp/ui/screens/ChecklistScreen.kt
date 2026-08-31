package com.example.asthmaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
fun ChecklistScreen(
    navController: NavController,
    checklistViewModel: ChecklistViewModel
) {
    val todos = checklistViewModel.todos
    var expandedTodoId by remember { mutableStateOf<String?>(null) }
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-dos bearbeiten") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Neues To-do hinzufügen") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                onClick = { navController.navigate("checklist/add") },
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorScheme.background)
        ) {
            items(todos) { todo ->
                TodoEditCard(
                    todo = todo,
                    expanded = expandedTodoId == todo.id,
                    onExpand = {
                        expandedTodoId = if (expandedTodoId == todo.id) null else todo.id
                    },
                    onEdit = { navController.navigate("checklist/edit/${todo.id}") }
                )
            }
        }
    }
}

@Composable
fun TodoEditCard(
    todo: Todo,
    expanded: Boolean,
    onExpand: () -> Unit,
    onEdit: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpand() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        todo.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.onSurface
                    )
                    Text(
                        todo.description ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    todo.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = colorScheme.onSurfaceVariant
                )
            }

            if (expanded) {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("Wiederholung: ${todo.repeat}", color = colorScheme.onSurfaceVariant)
                    if (todo.dosePerInhalation != null) {
                        Text(
                            "Dosis pro Inhalation: ${todo.dosePerInhalation} ${todo.doseUnit}",
                            color = colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Dosis insgesamt: ${todo.totalDose} ${todo.doseUnit}",
                            color = colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        "Benachrichtigung: ${if (todo.notification) "Ja" else "Nein"}",
                        color = colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onEdit,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = colorScheme.primary
                            )
                        ) {
                            Text("Bearbeiten")
                        }
                    }
                }
            }
        }
    }
}