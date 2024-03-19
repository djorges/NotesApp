package com.example.notes.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.notes.R
import com.example.notes.domain.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
){
    val items = arrayListOf(
        NavDrawerItem(
            title = stringResource(id = R.string.notes_name),
            route = Routes.Notes.route,
            icon = R.drawable.baseline_list_24,
            contentDescription = stringResource(id = R.string.notes_name)
        ),
        NavDrawerItem(
            title = stringResource(id = R.string.about_name),
            route = Routes.About.route,
            icon = R.drawable.baseline_help_24,
            contentDescription = stringResource(id = R.string.about_name)
        ),
        NavDrawerItem(
            title = stringResource(id = R.string.settings_name),
            route = Routes.Settings.route,
            icon = R.drawable.baseline_settings_24,
            contentDescription = stringResource(id = R.string.settings_name)
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalDrawerSheet(
        modifier = Modifier
            .requiredWidth(265.dp)
            .fillMaxHeight()
    ){
        //Drawer Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp)
        ){
            Text(
                modifier = Modifier.padding(top = 50.dp),
                text = stringResource(id = R.string.app_name),
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        //Drawer Body
        LazyColumn(modifier){
            items(items){item ->
                NavigationDrawerItem(
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(6.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.contentDescription
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        //Close Nav Drawer
                        coroutineScope.launch { drawerState.close() }

                        //Navigate if route has changed
                        if(currentRoute != item.route){
                            navController.navigate(item.route)
                        }
                    }
                )
            }
        }
    }
}

data class NavDrawerItem(
    val title: String,
    val icon: Int,
    val contentDescription: String?,
    val route: String,
)