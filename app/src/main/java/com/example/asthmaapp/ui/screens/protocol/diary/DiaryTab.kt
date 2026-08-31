package com.example.asthmaapp.ui.screens.protocol.diary

import androidx.compose.runtime.Composable
import com.example.asthmaapp.model.DiaryEntry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.asthmaapp.R

@Composable
fun DiaryTab() {
    val mockEntries = remember {
        mutableStateListOf(
            DiaryEntry(
                "24.05.2025",
                "Nebenwirkungen beobachtet",
                "Heute nach der Einnahme von Montelukast gegen 20:00 Uhr leichte Kopfschmerzen..."
            ),
            DiaryEntry(
                "23.05.2025",
                "Nebenwirkungen beobachtet",
                "Heute war ein Tag voller Höhen und Tiefen..."
            )
        )
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- Search Bar with Icons ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(stringResource(id = R.string.search_diary)) },
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            trailingIcon = {
                Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.today_24px),
                        contentDescription = "Calendar Today",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // --- Button Below Search ---
        Button(
            onClick = { /* mocked */ },
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
                    // tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(4.dp)) // Adjust width as needed
                Text(stringResource(id = R.string.new_entry), style = MaterialTheme.typography.labelLarge)
            }
        }
            Text(stringResource(id = R.string.may), style = MaterialTheme.typography.titleMedium)
            // --- Mock Entries ---
            mockEntries.forEach { entry ->
                DiaryEntryCard(
                    entry = entry,
                    onDeleteClick = { mockEntries.remove(entry) }
                )
            }
        }
    }
