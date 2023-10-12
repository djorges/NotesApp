package com.example.notes.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.notes.R
import com.example.notes.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(
    viewModel: MainViewModel,
    onDismissDialog: () -> Unit = {},
    onConfirmDialog: () -> Unit = {}
){
    val state by viewModel.mainState.collectAsState()

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                //Title and Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(
                            modifier = Modifier.clickable { onDismissDialog() },
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = stringResource(R.string.dialog_add_btn_cancel)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(R.string.dialog_add_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = onConfirmDialog
                    ){
                        Text(text = stringResource(R.string.dialog_add_btn_create))
                    }
                }

                //Fields
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.dialog_add_input_title)) },
                    value = state.title,
                    onValueChange = { viewModel.setTitle(it) },
                    placeholder = { Text(text = stringResource(R.string.dialog_add_input_title))}
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.dialog_add_input_note)) },
                    value = state.note,
                    onValueChange ={ viewModel.setNote(it) },
                    placeholder = { Text(text = stringResource(R.string.dialog_add_placehold_note))},
                    minLines = 5
                )
            }
        }
    }
}