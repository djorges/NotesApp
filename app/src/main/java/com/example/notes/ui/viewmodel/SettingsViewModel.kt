package com.example.notes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.repository.PreferencesRepository
import com.example.notes.data.dto.AppLanguage
import kotlinx.coroutines.flow.SharingStarted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefsRepository: PreferencesRepository
): ViewModel() {
    val darkModeState = prefsRepository.getDarkMode().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    val languageState = prefsRepository.getLanguage().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AppLanguage("English", "en")
    )

    fun saveDarkMode(isActive: Boolean){
        viewModelScope.launch(Dispatchers.IO + handler) {
            prefsRepository.saveDarkMode(isActive)
        }
    }

    fun saveLanguage(lang:String, langCode:String){
        viewModelScope.launch(Dispatchers.IO + handler) {
            prefsRepository.saveLanguage(
                AppLanguage(lang, langCode)
            )
        }
    }


    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("NOTES_APP", "Error Message: ${throwable.message}")
    }
}