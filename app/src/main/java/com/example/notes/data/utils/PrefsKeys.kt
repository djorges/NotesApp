package com.example.notes.data.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PrefsKeys {
    val IS_DARK_MODE_ACTIVE = booleanPreferencesKey("dark_mode")
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    val SELECTED_LANGUAGE_CODE = stringPreferencesKey("selected_language_code")
}