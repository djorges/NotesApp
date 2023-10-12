package com.example.notes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.repository.INotesRepository
import com.example.notes.domain.Note
import com.example.notes.domain.SortType
import com.example.notes.domain.toEntity
import com.example.notes.domain.toModel
import com.example.notes.ui.state.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: INotesRepository
): ViewModel() {
    //List State
    private val _sortType = MutableStateFlow(SortType.TITLE_DESC)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _notes = _sortType
        .flatMapLatest { sortType ->
            //Apply sort
            when(sortType){
                SortType.TITLE_DESC -> repository.getAllOrderedByTitleDesc()
                SortType.TITLE_ASC -> repository.getAllOrderedByTitleAsc()
                SortType.DATE_UPDATED_DESC -> repository.getAllOrderedByDateUpdatedDesc()
                SortType.DATE_UPDATED_ASC -> repository.getAllOrderedByDateUpdatedAsc()
            }
        }
        .map { it.map { entity -> entity.toModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    //General State
    private val _state = MutableStateFlow(NoteState())
    val mainState = combine(_state, _sortType, _notes){ state, sortType, notes ->
        state.copy(
            notes = notes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    /* Crud Methods */
    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.delete(note.toEntity())
        }
    }

    suspend fun getNoteById(id: Int): Note{
        return withContext(Dispatchers.IO) {
            repository.getNoteById(id).toModel()
        }
    }

    fun saveContact() {
        mainState.value.let{
            //Check not blank fields
            if(it.title.isNotBlank() || it.note.isNotBlank()){
                viewModelScope.launch(Dispatchers.IO + handler) {
                    repository.upsert(Note( title = it.title, note = it.note).toEntity())
                }
                resetNote()
            }
        }
    }

    /* Details Screen */
   /* fun setNote(note:Note){
        _state.update { st -> st.copy(
            id = note.id,
            title = note.title,
            note = note.note,
            imageUri = note.imageUri.toString(),
            dateUpdated = note.dateUpdated
        )}
    }*/

    private fun resetNote(){
        //Reset state
        _state.update { st -> st.copy(
            isAddingNote = false,
            title = "",
            note = "",
            imageUri = ""
        )}
    }

    /* Dialog Sort Note*/
    fun sortContacts(sortType: SortType){
        _sortType.update {sortType}
    }

    fun setIsSortingNote(boolean: Boolean){
        _state.update {it.copy(
            isSortingNote = boolean
        )}
    }

    /* Dialog Add Note*/
    fun setIsAddingNote(boolean: Boolean){
        _state.update {it.copy(
            isAddingNote = boolean
        )}
    }

    fun setTitle(title: String){
        _state.update {it.copy(
            title = title
        )}
    }

    fun setNote(note: String){
        _state.update {it.copy(
            note = note
        )}
    }

    fun setImageUri(imageUri:String){
        _state.update {it.copy(
            imageUri = imageUri
        )}
    }

    private val handler = CoroutineExceptionHandler { context, exception ->
        Log.e(APP_TAG,"Caught ${exception.message}")
    }
    companion object {
        private const val APP_TAG = "NOTES_APP"
    }
}