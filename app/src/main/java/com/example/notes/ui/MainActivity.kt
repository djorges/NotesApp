package com.example.notes.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.R
import com.example.notes.domain.Routes
import com.example.notes.ui.component.DrawerContent
import com.example.notes.ui.component.Toolbar
import com.example.notes.ui.screen.AboutScreen
import com.example.notes.ui.screen.NotesScreen
import com.example.notes.ui.screen.SettingsScreen
import com.example.notes.ui.theme.NotesTheme
import com.example.notes.ui.viewmodel.MainViewModel
import com.example.notes.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notesViewModel: MainViewModel by viewModels()
    private val settingsViewModel:SettingsViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //State
            val darkModeState = settingsViewModel.darkModeState.collectAsState()
            val languageState = settingsViewModel.languageState.collectAsState()
            val searchText = notesViewModel.searchText.collectAsState()
            val keyboardController = LocalSoftwareKeyboardController.current

            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(
                initialValue = DrawerValue.Closed,
                confirmStateChange = { value ->
                    if (value == DrawerValue.Open) {
                        keyboardController?.hide()
                    }
                    true
                }
            )

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
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Toolbar (
                                sortButtonAction = {notesViewModel.setIsSortingNote(true)},
                                searchFieldAction = { notesViewModel.onSearchTextChange(it)},
                                searchText = searchText.value,
                                navController = navController,
                                navigationIconAction = { coroutineScope.launch { drawerState.open() }}
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                onClick = { notesViewModel.setIsAddingNote(true) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(id = R.string.fab_text),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        }
                    ){ padding ->
                        //Nav Host
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Notes.route
                        ) {
                            composable(Routes.Notes.route) {
                                NotesScreen(
                                    viewModel = notesViewModel,
                                    modifier = Modifier.padding(padding),
                                    onClickItem = { note ->
                                        notesViewModel.setNoteState(note)

                                        //show dialog
                                        notesViewModel.setIsAddingNote(true)
                                    }
                                )
                            }
                            composable(Routes.Settings.route) {
                                SettingsScreen(
                                    modifier = Modifier.padding(padding),
                                    onDarkModeChecked = {
                                        settingsViewModel.saveDarkMode(it)
                                    },
                                    onLanguageChange = {
                                        //Persist in preferences
                                        settingsViewModel.saveLanguage(it.selectedLang, it.selectedLangCode)
                                        //Change language
                                        setLocale(it.selectedLangCode)
                                    },
                                    isDarkMode = darkModeState.value,
                                    selectedLanguage = languageState.value,
                                )
                            }
                            composable(Routes.About.route){
                                AboutScreen(
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * */
    private fun setLocale(langCode:String){
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(this, locale)
        }else{
            updateResourcesLocaleLegacy(this, locale)
        }
        this.recreate()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(
        context: Context,
        locale: Locale
    ) {
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        context.createConfigurationContext(configuration)
    }

    @SuppressWarnings("deprecation")
    private fun updateResourcesLocaleLegacy(
        context: Context,
        locale: Locale
    ){
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}