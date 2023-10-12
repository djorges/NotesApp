package com.example.notes.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.example.notes.domain.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    sortType: SortType = SortType.TITLE_DESC,
    onDismissDialog: () -> Unit = {},
    onConfirmDialog: (SortType) -> Unit = {}
) {
    var sortState by rememberSaveable { mutableStateOf(sortType) }

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismissDialog
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
                    text = stringResource(R.string.dialog_sort_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                //Show each filter
                SortType.values().forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = sortState == it,
                            onClick = {
                                if(sortState != it)
                                    sortState = it
                            }
                        )
                        Text(
                            text = when(it){
                                SortType.TITLE_ASC -> stringResource(R.string.dialog_sort_btn_titleasc)
                                SortType.TITLE_DESC -> stringResource(R.string.dialog_sort_btn_titledesc)
                                SortType.DATE_UPDATED_ASC -> stringResource(R.string.dialog_sort_btn_dateasc)
                                SortType.DATE_UPDATED_DESC -> stringResource(R.string.dialog_sort_btn_datedesc)
                            }
                        )
                    }
                }
                //Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = { onDismissDialog() },
                    ){
                        Text(text = stringResource(R.string.dialog_sort_btn_cancel))
                    }
                    Button(
                        onClick = { onConfirmDialog(sortState) }
                    ){
                        Text(text = stringResource(R.string.dialog_sort_btn_confirm))
                    }
                }
            }
        }
    }
}