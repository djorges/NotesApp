package com.example.notes.ui.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.notes.R
import com.example.notes.data.utils.DateUtils
import com.example.notes.domain.Note
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

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        //List of Contacts
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(mainState.notes){ note->
                NoteItem(
                    viewModel = viewModel,
                    onClickItem = onClickItem,
                    note = note
                )
            }
        }

        //Sort Dialog
        if(viewModel.isSortingNoteState){
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
        if(viewModel.isAddingNoteState){
            AddNoteDialog(
                viewModel = viewModel,
                onDismissDialog = {
                    viewModel.resetState()
                },
                onConfirmDialog = {
                    viewModel.saveNote()
                }
            )
        }
    }
}

@Composable
fun NoteItem(
    viewModel: MainViewModel,
    onClickItem: (Note) -> Unit = {},
    note: Note = Note(0,"","")
){
    var isMenuActionOpen by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClickItem(note) }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(note.color),
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(4.dp),
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    if(note.isPinned){
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_push_pin_24),
                            contentDescription = stringResource(R.string.dialog_add_btn_pin)
                        )
                    }
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Clip
                    )
                }
                IconButton(onClick = { isMenuActionOpen = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        contentDescription = stringResource(R.string.notes_btn_actions)
                    )
                    MenuActionItems(
                        viewModel = viewModel,
                        isMenuActionOpen = isMenuActionOpen,
                        note = note,
                        onDismissRequest = { isMenuActionOpen = false }
                    )
                }
            }
            Text(
                text = note.note
            )
            //List of Images
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(note.imageUris) {
                    AsyncImage(
                        modifier = Modifier
                            .width(50.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        placeholder = painterResource(R.drawable.baseline_camera_alt_24),
                        model = it,
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.dialog_add_img),
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = note.dateUpdated?.let { DateUtils.getStringFromDate(it) } ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MenuActionItems(
    viewModel: MainViewModel,
    isMenuActionOpen:Boolean,
    note: Note,
    onDismissRequest:() -> Unit
){
    val context = LocalContext.current

    DropdownMenu(
        expanded = isMenuActionOpen,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.menu_action_delete)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = stringResource(R.string.menu_action_delete)
                )
            },
            onClick = {
                viewModel.deleteNote(note)

                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.menu_action_share)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = stringResource(R.string.menu_action_share)
                )
            },
            onClick = {
                //Create Chooser
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, note.note)
                sendIntent.type = "text/plain"

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(context,shareIntent,null)

                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.menu_action_copy)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                    contentDescription = stringResource(R.string.menu_action_copy)
                )
            },
            onClick = {
                viewModel.copyNoteIntoClipboard(note.note)

                Toast
                    .makeText(context,"Copied", Toast.LENGTH_SHORT)
                    .show()

                onDismissRequest()
            }
        )
    }
}