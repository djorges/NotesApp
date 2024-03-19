package com.example.notes.ui.state

import com.example.notes.domain.Note
import com.example.notes.domain.SortType

data class ListState(
    val notes: List<Note> = emptyList(),
    val sortType: SortType = SortType.TITLE_DESC,
    val searchText:String = ""
)