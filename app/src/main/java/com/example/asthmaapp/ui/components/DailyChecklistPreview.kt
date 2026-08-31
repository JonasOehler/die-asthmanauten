package com.example.asthmaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.model.Todo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DailyChecklistPreview(
    todos: List<Todo>,
    onToggleDone: (Todo) -> Unit,
    onAddTodo: () -> Unit,
    onEditClick: () -> Unit // <-- Für den Stift-Button (zur checklist-Seite)
) {
    var showAll by remember { mutableStateOf(false) }

    val doneCount = todos.count { it.done }
    val totalCount = todos.size
    val progress = if (totalCount == 0) 0f else doneCount / totalCount.toFloat()
    val visibleTodos = if (showAll) todos else todos.take(2)

    val currentDate = LocalDate.now()
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
    ) {
        // Fortschritt
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("$doneCount/$totalCount", color = colorScheme.onSurface)
            Spacer(Modifier.width(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp),
                color = colorScheme.primary,
                trackColor = colorScheme.surfaceVariant
            )
        }

        // Titel + Stift
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Checkliste für den $formattedDate",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.onSurface
            )
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Bearbeiten",
                    tint = colorScheme.primary
                )
            }
        }

        if (todos.isEmpty()) {
            Button(
                onClick = onAddTodo,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Text("+ Checkliste erstellen")
            }
        } else {
            Text(
                "Anstehende To-dos",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            visibleTodos.forEach { todo ->
                ChecklistItem(todo = todo, onClick = { onToggleDone(todo) })
            }

            if (todos.size > 2) {
                TextButton(
                    onClick = { showAll = !showAll },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Create,
                        contentDescription = null,
                        tint = colorScheme.primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (showAll) "Weniger anzeigen…" else "Mehr anzeigen…",
                        color = colorScheme.primary
                    )
                }
            }
        }
    }
}