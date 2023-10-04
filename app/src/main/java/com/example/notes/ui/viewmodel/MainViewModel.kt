package com.example.notes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.repository.INotesRepository
import com.example.notes.domain.Note
import com.example.notes.domain.toEntity
import com.example.notes.domain.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * do impl and map entity
 * */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: INotesRepository
): ViewModel() {
    private val handler = CoroutineExceptionHandler { context, exception ->
        Log.e(APP_TAG,"Caught ${exception.message}")
    }
    val notesState = repository.getAllOrderedByTitle()
        .map { it.map { entity -> entity.toModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.delete(note.toEntity())
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.upsert(note.toEntity())
        }
    }

    fun insertNote(title:String, note:String, imageUri:String?){
        val memoryNote = Note(title = title, note = note, imageUri = imageUri)
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.upsert(memoryNote.toEntity())
        }
    }

    suspend fun getNoteById(id: Int): Note{
        return repository.getNoteById(id).toModel()
    }

    companion object {
        private const val APP_TAG = "NOTES_APP"
    }
}