package com.example.asthmaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.asthmaapp.model.ACTEntry
import com.example.asthmaapp.ui.screens.BarcodeScannerScreen
import com.example.asthmaapp.model.InhalatorRepository
import com.example.asthmaapp.model.MedicationRepository
import com.example.asthmaapp.model.MedicationViewModel
import com.example.asthmaapp.model.NavBarItem
import com.example.asthmaapp.model.ChecklistViewModel
import com.example.asthmaapp.ui.components.MainNavigationBar
import com.example.asthmaapp.ui.components.MainTopAppBar
import com.example.asthmaapp.ui.screens.forecast.ForecastScreen
import com.example.asthmaapp.ui.screens.AddItemSelectionScreen
import com.example.asthmaapp.ui.screens.InhalatorDetailScreen
import com.example.asthmaapp.ui.screens.MedicationListScreen
import com.example.asthmaapp.ui.screens.OverviewScreen
import com.example.asthmaapp.ui.screens.ChecklistScreen
import com.example.asthmaapp.ui.screens.TodoFormScreen
import com.example.asthmaapp.ui.screens.profile.ProfileScreen
import com.example.asthmaapp.ui.theme.AsthmaAppTheme

import com.example.asthmaapp.ui.screens.protocol.ProtocolScreen
import com.example.asthmaapp.ui.screens.protocol.act.ACTQuestionnaireScreen


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteFile("profile_data.json")
        setContent {
            val medicationViewModel = viewModel { MedicationViewModel(applicationContext) }

            val checklistViewModel: ChecklistViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory(application)
            )

            val navController = rememberNavController()

            val inhalatorRepository = remember { InhalatorRepository(applicationContext) }
            val medicationRepository = remember { MedicationRepository(applicationContext) }
            val actEntries = remember { mutableStateListOf<ACTEntry>() }
            AsthmaAppTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                Scaffold(
                    topBar = { MainTopAppBar(navController, scrollBehavior) },
                    bottomBar = { MainNavigationBar(NavBarItem.bottomNavItems, navController) },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavBarItem.Overview.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(NavBarItem.Overview.route) {
                            OverviewScreen(navController, checklistViewModel)
                        }
                        composable(NavBarItem.Medication.route) {
                            MedicationListScreen(navController, medicationViewModel)
                        }
                        composable(
                            route = "inhalator_detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val inhalatorId = backStackEntry.arguments?.getString("id") ?: ""
                            InhalatorDetailScreen(navController, inhalatorId, inhalatorRepository)
                        }
                        composable(NavBarItem.Protocol.route) { backStackEntry ->
                            val startTab = backStackEntry.savedStateHandle.get<String>("start_protocol_tab") ?: "diary"
                            ProtocolScreen(navController, actEntries, startTab)
                            LaunchedEffect(backStackEntry) {
                                val newActEntry = backStackEntry.savedStateHandle.get<ACTEntry>("new_act_entry")
                                if (newActEntry != null) {
                                    actEntries.add(0, newActEntry)
                                    backStackEntry.savedStateHandle.remove<ACTEntry>("new_act_entry")
                                }
                                backStackEntry.savedStateHandle.remove<String>("start_protocol_tab")
                            }
                        }
                        composable(NavBarItem.Forecast.route) {
                            ForecastScreen()
                        }

                        composable("profile") {
                            ProfileScreen(navController)
                        }

                        // ----------- Checkliste und TodoForm ----------
                        composable("checklist") {
                            ChecklistScreen(navController, checklistViewModel)
                        }
                        composable("checklist/add") {
                            TodoFormScreen(
                                navController = navController,
                                checklistViewModel = checklistViewModel,
                                editTodoId = null // Beim Hinzufügen: kein TodoId!
                            )
                        }
                        // ----------- NEU: Bearbeiten-Route -----------
                        composable(
                            "checklist/edit/{todoId}",
                            arguments = listOf(navArgument("todoId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val todoId = backStackEntry.arguments?.getString("todoId")
                            if (todoId != null) {
                                TodoFormScreen(
                                    navController = navController,
                                    checklistViewModel = checklistViewModel,
                                    editTodoId = todoId // das To-do mit dieser ID bearbeiten
                                )
                            }
                        }
                        // ---------------------------------------------
                        composable(
                            route = "add_item/{itemType}",
                            arguments = listOf(navArgument("itemType") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val itemType =
                                backStackEntry.arguments?.getString("itemType") ?: "medication"
                            AddItemSelectionScreen(navController, itemType)
                        }
                        composable(
                            route = "barcode_scan/{itemType}",
                            arguments = listOf(navArgument("itemType") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val itemType =
                                backStackEntry.arguments?.getString("itemType") ?: "medication"
                            BarcodeScannerScreen(navController, itemType, medicationViewModel)
                        }
                        composable("act_questionnaire") { backStackEntry ->
                            // NO LONGER ADDING ACT ENTRY HERE
                            // The new ACTEntry will be handled by the ProtocolScreen
                            ACTQuestionnaireScreen(
                                onQuestionnaireFinished = { newEntry ->
                                    // Set the new ACT entry on the previousBackStackEntry's savedStateHandle
                                    // (which belongs to ProtocolScreen)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("new_act_entry", newEntry)

                                    // Set the start tab for ProtocolScreen when it resumes
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("start_protocol_tab", "act")

                                    navController.popBackStack() // Go back to ProtocolScreen
                                },
                                onCancel = {
                                    navController.popBackStack() // Just go back without adding
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Optional für Tests:
@Composable
fun PlaceholderItems() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(100) {
            Text(
                text = "Item$it", modifier = Modifier.padding(16.dp)
            )
        }
    }
}
