package com.example.notes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.domain.Note
import com.example.notes.domain.Routes
import com.example.notes.ui.component.DrawerContent
import com.example.notes.ui.component.ScaffoldContainer
import com.example.notes.ui.screen.DetailsScreen
import com.example.notes.ui.screen.NotesScreen
import com.example.notes.ui.screen.SettingsScreen
import com.example.notes.ui.theme.NotesTheme
import com.example.notes.ui.viewmodel.MainViewModel
import com.example.notes.ui.viewmodel.SettingsViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notesViewModel: MainViewModel by viewModels()
    private val settingsViewModel:SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //State
            val darkModeState = settingsViewModel.darkModeState.collectAsState()

            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            NotesTheme(
                darkTheme = darkModeState.value
            ){
                //Drawer
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            navController = navController,
                            coroutineScope = coroutineScope,
                            drawerState = drawerState
                        )
                    },
                    gesturesEnabled = true
                ) {
                    ScaffoldContainer(
                        navController = navController,
                        addButtonAction = { notesViewModel.setIsAddingNote(true) },
                        iconButtonAction = { coroutineScope.launch { drawerState.open() }}
                    ) { padding ->
                        //Nav Host
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Notes.route
                        ) {
                            composable(Routes.Settings.route) {
                                SettingsScreen(
                                    viewModel = settingsViewModel,
                                    isDarkMode = darkModeState.value
                                )
                            }
                            composable(Routes.Notes.route) {
                                NotesScreen(
                                    viewModel = notesViewModel,
                                    modifier = Modifier.padding(padding),
                                    onClickItem = { note ->
                                        val json = Gson().toJson(note)
                                        navController.navigate(Routes.Details.route+"/$json")
                                    }
                                )
                            }
                            composable(
                                route = Routes.Details.route+"/{item}",
                                arguments = listOf(navArgument("item"){
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("item")?.let {
                                    DetailsScreen(
                                        note = Gson().fromJson(it, Note::class.java),
                                        viewModel = notesViewModel,
                                        modifier = Modifier.padding(padding)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}