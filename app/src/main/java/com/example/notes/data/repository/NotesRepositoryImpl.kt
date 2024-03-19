package com.example.notes.data.repository

import com.example.notes.data.db.NotesDao
import com.example.notes.data.entity.NoteEntity
import com.example.notes.data.service.ClipboardService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val notesDao:NotesDao,
    private val clipboardService: ClipboardService
): INotesRepository {
    override suspend fun insert(entity: NoteEntity) {
        notesDao.insert(entity)
    }

    override suspend fun update(entity: NoteEntity) {
        notesDao.update(entity)
    }

    override suspend fun delete(entity: NoteEntity) {
        notesDao.delete(entity)
    }

    override suspend fun getNoteById(id: Int): NoteEntity {
        return notesDao.getNoteById(id)
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        return notesDao.getAll()
    }

    override fun getAllByTitle(title: String): Flow<List<NoteEntity>> {
        return notesDao.getAllByTitle(title)
    }

    override suspend fun copyNoteIntoClipboard(note:String) {
        clipboardService.copyNoteIntoClipboard(note)
    }
}