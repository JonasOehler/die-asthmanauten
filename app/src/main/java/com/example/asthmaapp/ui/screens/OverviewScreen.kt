package com.example.asthmaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.asthmaapp.ui.components.DailyChecklistPreview
import com.example.asthmaapp.model.ChecklistViewModel

@Composable
fun OverviewScreen(
    navController: NavController,
    checklistViewModel: ChecklistViewModel = viewModel()
) {
    val todos = checklistViewModel.todos
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 60.dp)
    ) {
        // Info-Buttons
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoButton(
                text = "Inhalier-Level",
                icon = Icons.Filled.Star,
                background = colorScheme.primaryContainer,
                contentColor = colorScheme.onPrimaryContainer,
                modifier = Modifier.weight(1f)
            )
            InfoButton(
                text = "7 Tage in Folge",
                icon = Icons.Filled.Favorite,
                background = colorScheme.secondaryContainer,
                contentColor = colorScheme.onSecondaryContainer,
                modifier = Modifier.weight(1f)
            )
        }

        // Checkliste-Vorschau
        DailyChecklistPreview(
            todos = todos,
            onToggleDone = { checklistViewModel.toggleDone(it) },
            onAddTodo = { navController.navigate("checklist/add") },
            onEditClick = { navController.navigate("checklist") } // <- Stift-Button
        )

        Spacer(Modifier.height(16.dp))

        // Tipp des Tages
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Tipp des Tages",
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Achten Sie auf Ihre Atemtechnik! Langsames und tiefes Atmen durch die Nase kann helfen, Asthma-Symptome zu lindern. Üben Sie regelmäßig Atemübungen, um Ihre Lungenfunktion zu stärken und Stress abzubauen.",
                    color = colorScheme.onSurface,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun InfoButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /* TODO */ },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(6.dp))
        Text(text)
    }
}
