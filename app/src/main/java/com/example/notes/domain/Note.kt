package com.example.notes.domain

import java.util.Date

data class Note(
    val id:Int? = null,
    val title:String = "",
    val note:String = "",
    val color:Long = 0xFFFFFF8D,
    val isPinned: Boolean = false,
    val dateUpdated: Date? = null,
    val imageUris:List<String> = emptyList()
)