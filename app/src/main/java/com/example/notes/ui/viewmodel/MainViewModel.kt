package com.example.notes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.repository.INotesRepository
import com.example.notes.domain.Note
import com.example.notes.domain.SortType
import com.example.notes.domain.toEntity
import com.example.notes.domain.toModel
import com.example.notes.ui.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: INotesRepository
): ViewModel() {
    var isAddingNoteState by mutableStateOf(false)
    var isSortingNoteState by mutableStateOf(false)
    var isSearching by mutableStateOf(false)

    //Sort, Filters & Search State
    private val _sortType = MutableStateFlow(SortType.TITLE_DESC)
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _notes = searchText
        .onEach {isSearching = true }
        .flatMapLatest {
            if(it.isBlank()){
                repository.getAll()
            }else{
                repository.getAllByTitle(it)
            }
        }
        .onEach { isSearching = false }
        .map { list ->
            list.map { entity -> entity.toModel() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    //Note State
    private val _noteState = MutableStateFlow(Note())
    val noteState = _noteState.asStateFlow()

    private val _mainState = MutableStateFlow(ListState())
    val mainState = combine(_mainState, _sortType, _notes, _searchText){ state, sortType, notes, searchText ->
        val sortedNotes =
            when(_sortType.value){
                SortType.TITLE_ASC -> notes.sortedBy { it.note }
                SortType.TITLE_DESC -> notes.sortedByDescending { it.note }
                SortType.DATE_UPDATED_ASC -> notes.sortedBy { it.dateUpdated }
                SortType.DATE_UPDATED_DESC -> notes.sortedByDescending { it.dateUpdated }
            }

        state.copy(
            notes = sortedNotes.partition { it.isPinned }.toList().flatten(),
            sortType = sortType,
            searchText = searchText
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ListState())

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(APP_TAG,"Caught ${exception.message}")
    }
    private val coroutineContext: CoroutineContext = Dispatchers.IO + handler

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }

    /* Crud Methods */
    fun deleteNote(note: Note) {
        viewModelScope.launch(coroutineContext) {
            repository.delete(note.toEntity())
        }
    }

    fun saveNote() {
        _noteState.value.let{
            //Check fields are not blank
            if(it.title.isNotBlank() || it.note.isNotBlank()){
                viewModelScope.launch(coroutineContext) {
                    if(it.id != null){
                        repository.update(it.toEntity())
                    }else{
                        repository.insert(it.toEntity())
                    }
                }

                resetState()
            }
        }
    }

    fun copyNoteIntoClipboard(note:String){
        viewModelScope.launch(coroutineContext) {
            repository.copyNoteIntoClipboard(note)
        }
    }

    /* Dialog Sort Note*/
    fun sortContacts(sortType: SortType){
        _sortType.update {sortType}
    }

    fun setIsSortingNote(boolean: Boolean){
        isSortingNoteState = boolean
    }

    /* Dialog Add Note*/
    fun setIsAddingNote(boolean: Boolean){
        isAddingNoteState = boolean
    }

    fun setTitle(title: String){
        _noteState.update {it.copy(
            title = title
        )}
    }

    fun setTogglePin(isPinned: Boolean){
        _noteState.update {it.copy(
            isPinned = !isPinned
        )}
    }

    fun setNote(note: String){
        _noteState.update {it.copy(
            note = note
        )}
    }

    fun setImageUris(imageUris: List<String>){
        _noteState.update {it.copy(
            imageUris = imageUris
        )}
    }

    fun setColor(color:Long){
        _noteState.update { it.copy(
            color = color
        )}
    }

    fun setNoteState(note: Note){
        _noteState.update { note }
    }

    fun resetState(){
        //Reset state
        _noteState.update { Note()}

        isAddingNoteState = false
    }

    companion object {
        private const val APP_TAG = "NOTES_APP"
    }
}