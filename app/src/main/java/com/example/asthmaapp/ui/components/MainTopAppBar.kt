package com.example.asthmaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.asthmaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute in listOf("overview", "medication", "protocol", "forecast")) {
        TopAppBar(
            title = {
                Text(getTitleForRoute(currentRoute))
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate("profile")
                }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(id = R.string.account_button_description)
                    )
                }

            },
            scrollBehavior = scrollBehavior
        )
    }
}

@Composable
fun getTitleForRoute(route: String?): String {
    return when (route) {
        "overview" -> stringResource(R.string.overview_title)
        "medication" -> stringResource(R.string.medication_title)
        "protocol" -> stringResource(R.string.protocol_title)
        "forecast" -> stringResource(R.string.forecast_title)
        else -> stringResource(R.string.app_name)
    }
}