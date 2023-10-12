package com.example.notes.ui.screen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.domain.Note
import com.example.notes.R
import com.example.notes.ui.component.AddNoteDialog
import com.example.notes.ui.component.SortDialog
import com.example.notes.ui.viewmodel.MainViewModel

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onClickItem: (Note) -> Unit = {}
){
    val mainState by viewModel.mainState.collectAsState()
    val state = rememberLazyGridState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        //Sort & Filter Options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_tune_24),
                    contentDescription = stringResource(R.string.notes_btn_filters)
                )
                Text(
                    text = stringResource(R.string.notes_btn_filters)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable { viewModel.setIsSortingNote(true) }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sort_24),
                    contentDescription = stringResource(R.string.notes_btn_sort)
                )
                Text(
                    text = stringResource(R.string.notes_btn_sort)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        //List of Contacts
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(mainState.notes){ note->
                NoteItem(
                    onClickItem = onClickItem,
                    note = note
                )
            }
        }

        //Sort Dialog
        if(mainState.isSortingNote){
            SortDialog(
                sortType = mainState.sortType,
                onDismissDialog = {  viewModel.setIsSortingNote(false) },
                onConfirmDialog = {
                    viewModel.sortContacts(it)

                    viewModel.setIsSortingNote(false)
                }
            )
        }

        //Add Contact Dialog
        if(mainState.isAddingNote){
            AddNoteDialog(
                viewModel = viewModel,
                onDismissDialog = { viewModel.setIsAddingNote(false) },
                onConfirmDialog = { viewModel.saveContact() }
            )
        }
    }
}

@Preview
@Composable
fun NoteItem(
    onClickItem: (Note) -> Unit = {},
    note: Note = Note(0,"","",null, "")
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit){
                detectTapGestures(
                    onPress = {onClickItem(note)},
                    onLongPress = {

                    }
                )
            },
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(4.dp),
    ){

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note.note,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.align(Alignment.End),
                text = note.dateUpdated ?: "",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}