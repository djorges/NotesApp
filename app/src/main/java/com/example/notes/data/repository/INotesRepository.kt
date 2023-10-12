package com.example.notes.data.repository

import com.example.notes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface INotesRepository {
    suspend fun upsert(entity: NoteEntity)
    suspend fun delete(entity: NoteEntity)
    suspend fun getNoteById(id:Int): NoteEntity
    fun getAllOrderedByTitleAsc(): Flow<List<NoteEntity>>
    fun getAllOrderedByTitleDesc(): Flow<List<NoteEntity>>
    fun getAllOrderedByDateUpdatedAsc(): Flow<List<NoteEntity>>
    fun getAllOrderedByDateUpdatedDesc(): Flow<List<NoteEntity>>
}