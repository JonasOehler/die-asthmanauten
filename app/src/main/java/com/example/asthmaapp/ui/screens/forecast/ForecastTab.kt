package com.example.asthmaapp.ui.screens.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.ExpandCircleDown
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NearbyError
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.R
import com.example.asthmaapp.model.Pollen
import com.example.asthmaapp.model.PollenCount
import com.example.asthmaapp.ui.theme.primaryLight
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.DoDisturbOn
import androidx.compose.material3.HorizontalDivider

@Composable
fun ForecastTab() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val currentDate = LocalDate.now()
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    var allPollensVisible by remember { mutableStateOf(true) }
    var mockPollens = listOf(
       // Pollen(R.string.all_pollens, PollenCount.MODERATE),
        Pollen(R.string.ragweed, PollenCount.HIGH),
        Pollen(R.string.sorrel, PollenCount.MODERATE),
        Pollen(R.string.mugwort, PollenCount.LOW),
        Pollen(R.string.birch, PollenCount.LOW),
        Pollen(R.string.beech, PollenCount.LOW),
        Pollen(R.string.oak, PollenCount.NONE),
        Pollen(R.string.alder, PollenCount.NONE),
        Pollen(R.string.ash, PollenCount.NONE),
        Pollen(R.string.grasses, PollenCount.NONE),
        Pollen(R.string.hazel, PollenCount.NONE),
        Pollen(R.string.poplar, PollenCount.NONE),
        Pollen(R.string.rye, PollenCount.NONE),
        Pollen(R.string.elm, PollenCount.NONE),
        Pollen(R.string.plantain, PollenCount.NONE),
        Pollen(R.string.willow, PollenCount.NONE)

        )

    var pollensState = remember { mutableStateListOf<Pollen>().apply { addAll(mockPollens) } }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(stringResource(id = R.string.berlin)) },
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            trailingIcon = {
                Row {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Calendar Today",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
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
        Card( //WEATHER WIND HUMIDITY
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // This now spaces out the three inner Rows
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudQueue,
                        contentDescription = "Temperature"
                    )
                    Text(
                        "29°C",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Navigation,
                        contentDescription = "Wind"
                    )
                    Text(
                        "18 km/h",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = "Humidity"
                    )
                    Text(
                        "60 %",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Card( //POLLEN LEVELS
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.pollen_level),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${formattedDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = allPollensVisible,
                            onClick = {
                                allPollensVisible = !allPollensVisible
                                pollensState.forEach { it.isVisible.value = allPollensVisible }
                            },
                            label = { Text(stringResource(R.string.all_pollens)) }
                        )
                    }

                    items(pollensState) { pollen ->
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = pollen.isVisible.value,
                            onClick = {
                                pollen.isVisible.value = !pollen.isVisible.value
                                allPollensVisible = pollensState.all { it.isVisible.value }
                            },
                            label = { Text(stringResource(pollen.nameRes)) }
                        )
                    }
                }
                pollensState.filter { it.isVisible.value }.forEach { pollen ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = stringResource(pollen.nameRes),
                            modifier = Modifier
                                .width(100.dp), // <--- ADJUST THIS WIDTH to accommodate your longest pollen name + desired gap
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )


                        Spacer(modifier = Modifier.width(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                        ) {
                            val iconImageVector = when (pollen.count) {
                                PollenCount.NONE -> Icons.Default.CheckCircle
                                PollenCount.LOW -> Icons.Default.Warning
                                PollenCount.MODERATE -> Icons.Default.DoDisturbOn
                                PollenCount.HIGH -> Icons.Default.Dangerous
                            }
                            val iconTint = when (pollen.count) {
                                PollenCount.NONE -> Color(0xFF00E400)
                                PollenCount.LOW -> Color(0xFFFFFF00)
                                PollenCount.MODERATE -> Color(0xFFFF5C00)
                                PollenCount.HIGH -> Color(0xFFFF0000)
                            }

                            Icon(
                                imageVector = iconImageVector,
                                tint = iconTint,
                                contentDescription = pollen.count.name,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(
                                    id = when (pollen.count) {
                                        PollenCount.NONE -> R.string.no_pollen
                                        PollenCount.LOW -> R.string.low_pollen_count
                                        PollenCount.MODERATE -> R.string.moderate_pollen_count
                                        PollenCount.HIGH -> R.string.high_pollen_count
                                    }
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "stringResource(R.string.info)",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 8.dp)   )
                    }
                    // Add a HorizontalDivider after each row for visual separation
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
            Card( //MAP CARD
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.air_pollution),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.NearbyError,
                            contentDescription = "Nearby Station",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp * 1.2f) // Adjust icon size to match text

                        )
                        Text(
                            text = "Brackenheim Station",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Card( //AQI CARDD
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFC4F7C6)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)
                            ) {
                                // The US AQI Card
                                Card(
                                    modifier = Modifier.size(70.dp),
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = Color(0xFF00E400)),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "50",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "US AQI",
                                            style = MaterialTheme.typography.labelSmall,
                                        )
                                    }
                                }
                                Text(
                                    text = stringResource(id = R.string.good),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                                //  Icon at the end
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Checkmark Icon",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color(0xFF00E400)
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Information",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp * 1.2f) // Adjust icon size to match text

                        )
                        Text(
                            text = stringResource(id = R.string.air_pollution_info),
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.air_quality_map),
                        contentDescription = "Air quality map",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
    }

}

