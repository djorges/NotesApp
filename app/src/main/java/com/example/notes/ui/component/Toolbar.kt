package com.example.notes.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.notes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController:NavController,
    navigationIconAction: () -> Unit = {}
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route

    //TODO: Add route/label converter
    if(currentRoute == "Details/{item}"){
        currentRoute = "Details"
    }
    TopAppBar(
        title = { Text(text = currentRoute.toString()) },
        navigationIcon = {
            IconButton(onClick = navigationIconAction) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(R.string.toolbar_btn_menu_action),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        scrollBehavior = scrollBehavior,
    )
}