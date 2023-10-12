package com.example.notes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.example.notes.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    isDarkMode: Boolean
) {
    val titleTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ){
        //Section Title
        Text(
            text = stringResource(R.string.settings_name),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = titleTextStyle
        )

        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.settings_txt_general)
            )
        }
        //Sections
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            //DarkMode
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.settings_txt_darkmode),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkMode,
                onCheckedChange = {
                    viewModel.saveDarkMode(it)
                }
            )
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.settings_txt_language),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            //List of Languages

        }
    }
}