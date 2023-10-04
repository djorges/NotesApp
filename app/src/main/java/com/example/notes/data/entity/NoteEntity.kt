package com.example.notes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "note") val note:String,
    @ColumnInfo(name = "dateUpdated") val dateUpdated:String = getDateCreated(),
    @ColumnInfo(name = "imageUri") val imageUri:String? = null
)

fun getDateCreated():String{
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}