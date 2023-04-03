package com.example.mvvm_note_app.feature_note.presentation.note_list.di

import android.app.Application
import androidx.room.Room
import com.example.mvvm_note_app.feature_note.data.NoteDatabase
import com.example.mvvm_note_app.feature_note.data.repository.NoteRepositoryImpl
import com.example.mvvm_note_app.feature_note.domain.NoteRepository
import com.example.mvvm_note_app.feature_note.domain.use_cases.*
import com.example.mvvm_note_app.feature_note.model.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            getNote = GetNote(noteRepository),
            addNotes = AddNote(noteRepository),
            deleteNotes = DeleteNotes(noteRepository)
        )
    }
}