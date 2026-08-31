package com.example.asthmaapp.ui.screens.protocol

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.R


@Composable
fun PeakFlowTab() {

    var pef1 by remember { mutableStateOf("") }
    var pef2 by remember { mutableStateOf("") }
    var pef3 by remember { mutableStateOf("") }
    var checkedCough by remember { mutableStateOf(false)}
    var checkedSoB by remember { mutableStateOf(false)}
    var checkedPhlegm by remember { mutableStateOf(false)}


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
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ){
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.new_measurement),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "25. Mai 2025",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.clear_day24px),
                            contentDescription = "Clear Day Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "8:03",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    //PEF Value Rows
                    Row(modifier = Modifier.padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "1. ${stringResource(id = R.string.pef_value)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        OutlinedTextField(
                            value = pef1,
                            onValueChange = { newValue -> pef1 = newValue },
                            shape = RoundedCornerShape(4.dp),
                            singleLine = true,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(60.dp)
                                .height(40.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.l_min),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "2. ${stringResource(id = R.string.pef_value)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        OutlinedTextField(
                            value = pef2,
                            onValueChange = { newValue -> pef2 = newValue },
                            shape = RoundedCornerShape(4.dp),
                            singleLine = true,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(60.dp)
                                .height(40.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.l_min),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "3. ${stringResource(id = R.string.pef_value)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        OutlinedTextField(
                            value = pef3,
                            onValueChange = { newValue -> pef3 = newValue },
                            shape = RoundedCornerShape(4.dp),
                            singleLine = true,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(60.dp)
                                .height(40.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.l_min),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    Text(
                        text = stringResource(id = R.string.tap_symptoms),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checkedCough,
                            onCheckedChange = { checkedCough = it }
                        )
                        Text(
                            stringResource(id = R.string.cough)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checkedSoB,
                            onCheckedChange = { checkedSoB = it }
                        )
                        Text(
                            stringResource(id = R.string.sob)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checkedPhlegm,
                            onCheckedChange = { checkedPhlegm = it }
                        )
                        Text(
                            stringResource(id = R.string.phlegm)
                        )
                    }
                    Row(
                        modifier = Modifier
                          //  .clickable(onClick = onClick)
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Information",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(MaterialTheme.typography.bodySmall.fontSize.value.dp * 1.2f) // Adjust icon size to match text

                        )
                        Text(
                            text = stringResource(id = R.string.symptoms_meaning_info),
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Button(
                            onClick = { /* mocked */ },
                            modifier = Modifier
                                .height(height = 32.dp)
                        ){
                            Text(stringResource(id = R.string.done))
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                text = "27.04.25 - 24.05.25",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.pef_measurement),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.last_result),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sentiment_dissatisfied),
                            contentDescription = "Frown",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = stringResource(id = R.string.critical),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.peakflow),
                        contentDescription = "Peak Flow Chart Screenshot",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

//@Composable
//fun Checkbox(checked: checked, onCheckedChange: () -> Unit) {
  //("Not yet implemented")
//}

fun openCalendar(){

}