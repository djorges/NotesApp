package com.example.notes.ui.state

import com.example.notes.domain.Note
import com.example.notes.domain.SortType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val sortType: SortType = SortType.TITLE_DESC,
    val isAddingNote:Boolean = false,
    val isSortingNote:Boolean = false,
    val isMenuActionsOpen:Boolean = false,

    val id: Int? = null,
    val title: String = "",
    val note: String = "",
    val imageUri: String = ""
)