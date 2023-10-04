package com.example.notes.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.notes.domain.Note
import com.example.notes.ui.viewmodel.MainViewModel

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onClickItem: (Note) -> Unit = {}
){
    val listState by viewModel.notesState.collectAsState()

    Text(
        text = "Hello World",
        modifier = modifier
    )
}