package com.example.asthmaapp.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.R


@Composable
fun ForecastScreen() {
    val tabs = listOf(R.string.today, R.string.tomorrow, R.string.day_after_tomorrow)
    var selectedTab by remember { mutableIntStateOf(0) }
    val indicatorShape = RoundedCornerShape(1.5.dp)

    Column( modifier = Modifier
        .fillMaxSize()
    )  {
        TabRow(  selectedTabIndex = selectedTab,
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {
                    val currentTabPosition = tabPositions[selectedTab]
                    val indicatorWidth = 59.dp

                    Box(
                        Modifier
                            .tabIndicatorOffset(currentTabPosition)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .width(indicatorWidth)
                                .height(3.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = indicatorShape
                                ))
                    }
                }
            }
        ) {
            tabs.forEachIndexed { index, titleResId -> // Renamed `title` to `titleResId` for clarity
                val isSelected = selectedTab == index
                Tab(
                    selected = isSelected,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = stringResource(id = titleResId),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary // Selected tab text color
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant // Unselected tab text color
                            }
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> ForecastTab()
            1 -> ForecastTab()
            2 -> ForecastTab()
        }
    }
}