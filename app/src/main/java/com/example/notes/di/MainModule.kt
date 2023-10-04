package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.data.db.MainDatabase
import com.example.notes.data.db.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(database: MainDatabase): NotesDao {
        return database.dao
    }

    private const val DB_NAME =  "notes.db"
}