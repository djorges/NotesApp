package com.example.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Upsert
    suspend fun upsert(entity: NoteEntity)

    @Delete
    suspend fun delete(entity: NoteEntity)

    @Query("SELECT * FROM notes WHERE notes.id=:id")
    suspend fun getNoteById(id:Int): NoteEntity

    @Query("SELECT * FROM notes ORDER BY title DESC")
    fun getAllOrderedByTitle(): Flow<List<NoteEntity>>
}