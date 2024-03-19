package com.example.notes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.notes.R
import com.example.notes.data.dto.AppLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onDarkModeChecked:(Boolean) -> Unit = {},
    onLanguageChange:(AppLanguage) -> Unit = {},
    isDarkMode: Boolean = false,
    selectedLanguage : AppLanguage
) {
    var languageDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    val languageList = listOf(
        AppLanguage("English","en"),
        AppLanguage("Español","es")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ){
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.settings_txt_general),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
        //Dark Mode
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_dark_mode_24),
                    contentDescription= stringResource(R.string.settings_txt_darkmode),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(R.string.settings_txt_darkmode),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Switch(
                checked = isDarkMode,
                onCheckedChange = onDarkModeChecked,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor= MaterialTheme.colorScheme.onSecondary,
                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        //Language
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_language_24),
                    contentDescription= stringResource(R.string.settings_txt_language),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(R.string.settings_txt_language),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null,
                modifier = Modifier.clickable { languageDialogState = true },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        if(languageDialogState){
            AlertDialog(
                modifier = Modifier,
                onDismissRequest = { languageDialogState = false }
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(15.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        //Title
                        Text(
                            text = stringResource(R.string.settings_txt_language),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        //Language List
                        languageList.forEach {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ){
                                RadioButton(
                                    selected = it.selectedLang == selectedLanguage.selectedLang,
                                    onClick = {
                                        if( it.selectedLang != selectedLanguage.selectedLang) {
                                            onLanguageChange(it)
                                        }
                                    }
                                )
                                Text(
                                    text = it.selectedLang
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}