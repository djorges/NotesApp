package com.example.notes.data.di

import com.example.notes.data.repository.NotesRepositoryImpl
import com.example.notes.data.repository.INotesRepository
import com.example.notes.data.repository.PreferencesRepository
import com.example.notes.data.repository.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNotesRepository(
        notesRepoImpl: NotesRepositoryImpl
    ): INotesRepository

    @Singleton
    @Binds
    abstract fun bindPreferencesRepository(
        prefsRepoImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}