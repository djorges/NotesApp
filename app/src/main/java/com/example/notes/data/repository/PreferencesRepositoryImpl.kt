package com.example.notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.notes.data.utils.AppLanguage
import com.example.notes.data.utils.PrefsKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferencesRepository{

    override fun getDarkMode(): Flow<Boolean> = dataStore.data
        .map{ prefs ->
            prefs[PrefsKeys.IS_DARK_MODE_ACTIVE] ?: false
        }

    override fun getLanguage(): Flow<AppLanguage> = dataStore.data
        .map { prefs ->
            AppLanguage(
                prefs[PrefsKeys.SELECTED_LANGUAGE] ?: "English",
                prefs[PrefsKeys.SELECTED_LANGUAGE_CODE] ?: "en"
            )
        }

    override suspend fun saveDarkMode(isActive: Boolean) {
        dataStore.edit { prefs ->
            prefs[PrefsKeys.IS_DARK_MODE_ACTIVE] = isActive
        }
    }

    override suspend fun saveLanguage(language: AppLanguage) {
        dataStore.edit {prefs ->
            prefs[PrefsKeys.SELECTED_LANGUAGE] = language.selectedLang
            prefs[PrefsKeys.SELECTED_LANGUAGE_CODE] = language.selectedLangCode
        }
    }
}