package com.example.notes.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.notes.R
import com.example.notes.domain.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    optionsMenuAction: () -> Unit = {},
    sortButtonAction: () -> Unit = {},
    searchFieldAction: (String) -> Unit = {},
    searchText:String = "",
    isSearching: Boolean = false,
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            if(currentRoute == Routes.Notes.route){
               SearchTopAppBars(
                   text = searchText,
                   onTextChange = searchFieldAction,
                   isSearching = isSearching
               )
            }else{
                Text(text = currentRoute.toString())
            }
        },
        navigationIcon = {
            IconButton(onClick = navigationIconAction) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(R.string.toolbar_btn_menu_action),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            if(currentRoute == Routes.Notes.route){
                //Sort & Filter Options
                IconButton(onClick = sortButtonAction) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_tune_24),
                        contentDescription = stringResource(id = R.string.notes_btn_sort),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun SearchTopAppBars(
    text: String = "",
    isSearching: Boolean = false,
    onTextChange:(String)->Unit = {},
    onCloseClicked:()->Unit = {}
){
    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    ){
        TextField(
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.colors(
                focusedTextColor= Color.LightGray,
                unfocusedTextColor= Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTrailingIconColor =  Color.LightGray,
                unfocusedTrailingIconColor =  Color.LightGray,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor =  Color.LightGray,
                cursorColor = Color.LightGray
            ),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    text = "Search note"
                )
            },
            textStyle = TextStyle(
                color = Color.LightGray,
                fontSize = 16.sp
            ),
            singleLine = true,
            leadingIcon = {
                  //Search button
                  IconButton(
                      onClick = { onTextChange("") }
                  ){
                      Icon(
                          imageVector = Icons.Filled.Search,
                          contentDescription = null,
                          tint = Color.LightGray
                      )
                  }
            },
            trailingIcon = {
                //Clear button
                if(text.isNotBlank()){
                    IconButton(
                        onClick = { onTextChange("") }
                    ){
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                    }
                }
            }
        )
    }
}