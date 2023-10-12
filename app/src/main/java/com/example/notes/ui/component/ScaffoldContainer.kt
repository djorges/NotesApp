package com.example.notes.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.notes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldContainer(
    modifier:Modifier = Modifier,
    addButtonAction: () -> Unit = {},
    iconButtonAction: () -> Unit = {},
    navController:NavController,
    content: @Composable (PaddingValues) -> Unit = {}
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Toolbar (
                scrollBehavior = scrollBehavior,
                navController = navController,
                navigationIconAction = iconButtonAction
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = addButtonAction
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.fab_text)
                )
            }
        }
    ){
        content(it)
    }
}