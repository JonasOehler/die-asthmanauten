package com.example.asthmaapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.asthmaapp.R

sealed class NavBarItem(
    @StringRes val titleRes: Int,
    val route: String,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    val badgeCount: Int? = null
) {
    object Overview : NavBarItem(
        titleRes = R.string.overview_title,
        route = "overview",
        selectedIconRes = R.drawable.baseline_home_filled_24,
        unselectedIconRes = R.drawable.outline_home_24,
        badgeCount = 3
    )
    object Medication : NavBarItem(
        titleRes = R.string.medication_title,
        route = "medication",
        selectedIconRes = R.drawable.baseline_pill_filled_24,
        unselectedIconRes = R.drawable.outline_pill_24,
    )
    object Protocol : NavBarItem(
        titleRes = R.string.protocol_title,
        route = "protocol",
        selectedIconRes = R.drawable.baseline_book_4_24,
        unselectedIconRes = R.drawable.outline_book_4_24,
    )
    object Forecast : NavBarItem(
        titleRes = R.string.forecast_title,
        route = "forecast",
        selectedIconRes = R.drawable.outline_allergy_24,
        unselectedIconRes = R.drawable.outline_allergy_24,
    )

    companion object {
        val bottomNavItems = listOf(Overview, Medication, Protocol, Forecast)
    }
}