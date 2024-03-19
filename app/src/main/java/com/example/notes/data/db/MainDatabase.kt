package com.example.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.data.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 6
)
@TypeConverters(TimestampConverter::class, ListConverter::class)
abstract class MainDatabase : RoomDatabase() {
    abstract val dao : NotesDao
}