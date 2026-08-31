package com.example.asthmaapp.ui.screens.protocol.act

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.asthmaapp.R
import com.example.asthmaapp.model.ACTEntry
import com.example.asthmaapp.model.ACTResultType
import com.example.asthmaapp.ui.screens.protocol.openCalendar
import java.util.UUID
import androidx.compose.foundation.lazy.items


@Composable
fun ACTTab( actEntries: List<ACTEntry>,
    // This callback is now used to trigger navigation, not to add the entry directly
            onNavigateToQuestionnaire: () -> Unit
) {

    val showQuestionnaire = remember { mutableStateOf(false) }
   // val questionnaireStep = remember { mutableStateOf(0) }
    val answers = remember { mutableStateListOf("", "", "", "", "") }
    val cardExpanded = remember { mutableStateOf(false) }


    // Create a mock ACTEntry for testing
    val mockACTEntry = remember {
        ACTEntry(
            id = UUID.randomUUID().toString(),
            date = "24.05.2025",
            score = 8, // A low score to ensure BAD resultType
            answers = listOf("1", "2", "1", "2", "1"), // Example answers
            resultType = ACTResultType.BAD // Fixed as BAD for now
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            item {
                Row {
                    TextButton(onClick = { openCalendar() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.today_24px),
                            contentDescription = "Calendar Today",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(stringResource(id = R.string.jump_to_date))
                    }
                }

                Button(
                    onClick = { onNavigateToQuestionnaire() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(height = 32.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            stringResource(id = R.string.new_entry),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }


            items(actEntries) { entry ->
                ACTEntryCard(entry = entry)
            }
        }

    }
}