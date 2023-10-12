package com.example.notes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.notes.data.db.TimestampConverter
import com.example.notes.data.utils.DateUtils
import java.util.Date

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,

    @ColumnInfo(name = "title")
    val title:String,

    @ColumnInfo(name = "note")
    val note:String,

    @ColumnInfo(name = "dateUpdated")
    @TypeConverters(TimestampConverter::class)
    val dateUpdated: Date = DateUtils.getCurrentTime(),

    @ColumnInfo(name = "imageUri")
    val imageUri:String? = null
)