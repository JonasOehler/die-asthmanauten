package com.example.asthmaapp.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.asthmaapp.model.NavBarItem
import kotlin.collections.contains

@Composable
fun MainNavigationBar(
    navBarItems: List<NavBarItem>,
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route

   if (currentRoute in listOf("overview", "medication", "protocol", "forecast")){
       NavigationBar {
           navBarItems.forEach { navBarItem ->
               val itemIsSelected = currentDestination?.hierarchy?.any {
                   it.route == navBarItem.route
               } == true

               NavigationBarItem(
                   selected = itemIsSelected,
                   onClick = {
                       navController.navigate(navBarItem.route){
                           launchSingleTop = true
                           restoreState = true
                       }
                   },
                   icon = {
                       NavBarIconView(
                           isSelected = itemIsSelected,
                           item = navBarItem
                       )
                   },
                   label = {
                       Text(text = stringResource(id = navBarItem.titleRes))
                   }
               )
           }
       }
   }
}

@Composable
fun NavBarIconView(
    isSelected: Boolean,
    item: NavBarItem
){
    BadgedBox(badge = { NavBarBadgeView(item.badgeCount)}) {
        Icon(
            painter = painterResource(
                id = if (isSelected) item.selectedIconRes else item.unselectedIconRes) ,
            contentDescription = stringResource(id = item.titleRes)
        )
    }

}

@Composable
fun NavBarBadgeView(count: Int? = null){
    if(count != null){
        Badge{
            Text(text = count.toString())
        }
    }
}