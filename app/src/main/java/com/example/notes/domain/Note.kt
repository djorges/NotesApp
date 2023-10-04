package com.example.notes.domain

data class Note(
    val id:Int? = null,
    val title:String,
    val note:String,
    val dateUpdated:String? = null,
    val imageUri:String? = null
)