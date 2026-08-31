package com.example.asthmaapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asthmaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemSelectionScreen(
    navController: NavController,
    itemType: String // "inhalator" oder "medication"
) {
    val titleRes = if (itemType == "inhalator") R.string.add_inhaler else R.string.add_medication
    val searchTitleRes = if (itemType == "inhalator") R.string.search_inhaler_title else R.string.search_medication_title
    val searchSubtitleRes = if (itemType == "inhalator") R.string.search_inhaler_subtitle else R.string.search_medication_subtitle
    val scanSubtitleRes = if (itemType == "inhalator") R.string.scan_inhaler_subtitle else R.string.scan_medication_subtitle

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = titleRes))
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_arrow_back_24dp),
                        contentDescription = stringResource(id = R.string.back_button_description)
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectionCard(
                icon = Icons.Outlined.Search,
                titleRes = searchTitleRes,
                subtitleRes = searchSubtitleRes,
                onClick = {
                    // TODO: Navigation zur Suchfunktion implementieren
                }
            )

            SelectionCard(
                icon = Icons.Outlined.QrCodeScanner,
                titleRes = R.string.scan_barcode_title,
                subtitleRes = scanSubtitleRes,
                onClick = {
                    navController.navigate("barcode_scan/$itemType")
                }
            )
        }
    }
}

@Composable
private fun SelectionCard(
    icon: ImageVector,
    @StringRes titleRes: Int,
    @StringRes subtitleRes: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = subtitleRes),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
