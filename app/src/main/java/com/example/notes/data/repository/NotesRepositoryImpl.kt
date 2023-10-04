package com.example.notes.data.repository

import com.example.notes.data.db.NotesDao
import com.example.notes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val notesDao:NotesDao
): INotesRepository {
    override suspend fun upsert(entity: NoteEntity) {
        notesDao.upsert(entity)
    }

    override suspend fun delete(entity: NoteEntity) {
        notesDao.delete(entity)
    }

    override suspend fun getNoteById(id: Int): NoteEntity {
        return notesDao.getNoteById(id)
    }

    override fun getAllOrderedByTitle(): Flow<List<NoteEntity>> {
        return notesDao.getAllOrderedByTitle()
    }
}