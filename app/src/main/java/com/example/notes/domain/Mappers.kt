package com.example.notes.domain

import com.example.notes.data.entity.NoteEntity

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        note = note,
        color = color,
        isPinned = isPinned,
        imageUris = imageUris
    )
}

fun NoteEntity.toModel(): Note{
    return Note(
        id = id,
        title = title,
        note = note,
        color = color,
        isPinned = isPinned,
        dateUpdated = dateUpdated,
        imageUris = imageUris
    )
}