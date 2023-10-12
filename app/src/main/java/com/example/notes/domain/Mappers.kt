package com.example.notes.domain

import com.example.notes.data.entity.NoteEntity
import com.example.notes.data.utils.DateUtils

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        note = note,
        imageUri = imageUri
    )
}

fun NoteEntity.toModel(): Note{
    return Note(
        id = id,
        title = title,
        note = note,
        dateUpdated = DateUtils.getStringFromDate(dateUpdated),
        imageUri = imageUri
    )
}