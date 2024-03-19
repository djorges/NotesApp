package com.example.notes.data.repository

import com.example.notes.data.dto.AppLanguage
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository{
    fun getDarkMode() : Flow<Boolean>
    fun getLanguage() : Flow<AppLanguage>
    suspend fun saveDarkMode(isActive: Boolean)
    suspend fun saveLanguage(language: AppLanguage)
}