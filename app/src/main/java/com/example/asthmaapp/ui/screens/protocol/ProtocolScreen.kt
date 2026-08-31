package com.example.asthmaapp.ui.screens.protocol

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asthmaapp.R
import com.example.asthmaapp.model.ACTEntry
import com.example.asthmaapp.model.ACTResultType
import com.example.asthmaapp.ui.screens.protocol.act.ACTTab
import com.example.asthmaapp.ui.screens.protocol.diary.DiaryTab
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun ProtocolScreen(
    navController: NavController,
    actEntries: MutableList<ACTEntry>,
    startTab: String = "diary",
    modifier: Modifier = Modifier
) {
    val tabsInfo = listOf( Pair(R.string.diary, "diary"),
        Pair(R.string.peak_flow, "peak_flow"),
        Pair(R.string.act, "act"))
    val tabTitles = tabsInfo.map { it.first }

    var selectedTab by remember {
        val initialIndex = tabsInfo.indexOfFirst { it.second == startTab }
        mutableIntStateOf(initialIndex.coerceAtLeast(0)) // Ensure index is not -1, default to 0
    }
    val indicatorShape = RoundedCornerShape(1.5.dp)

    LaunchedEffect(Unit) {
        if(actEntries.isEmpty()) {
            val mockACTEntry = ACTEntry(
            id = UUID.randomUUID().toString(),
            date = "24.05.2025",
            score = 8,
            answers = listOf("1", "2", "1", "2", "1"),
            resultType = ACTResultType.BAD)
            actEntries.add(mockACTEntry)
        }
    }

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
            tabTitles.forEachIndexed { index, titleResId ->
                val isSelected = selectedTab == index
                Tab(
                    selected = isSelected,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = stringResource(id = titleResId),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> DiaryTab()
            1 -> PeakFlowTab()
            2 -> ACTTab(
                actEntries = actEntries,
                onNavigateToQuestionnaire = { navController.navigate("act_questionnaire") }
            )
        }
    }
}