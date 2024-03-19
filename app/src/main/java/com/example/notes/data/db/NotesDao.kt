package com.example.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.notes.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: NoteEntity)

    @Update
    suspend fun update(entity: NoteEntity)

    @Delete
    suspend fun delete(entity: NoteEntity)

    @Query("SELECT * FROM notes WHERE notes.id=:id")
    suspend fun getNoteById(id:Int): NoteEntity

    @Query("SELECT * FROM notes WHERE 1")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :search || '%'")
    fun getAllByTitle(search:String): Flow<List<NoteEntity>>
}