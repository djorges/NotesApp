package com.example.notes.data.repository

import com.example.notes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface INotesRepository {
    suspend fun insert(entity: NoteEntity)
    suspend fun update(entity: NoteEntity)
    suspend fun delete(entity: NoteEntity)
    suspend fun getNoteById(id:Int): NoteEntity
    fun getAll(): Flow<List<NoteEntity>>
    fun getAllByTitle(title:String): Flow<List<NoteEntity>>
    suspend fun copyNoteIntoClipboard(note:String)
}