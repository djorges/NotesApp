package com.example.notes.di

import com.example.notes.data.repository.NotesRepositoryImpl
import com.example.notes.data.repository.INotesRepository
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
}