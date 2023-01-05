package com.example.mvvm_note_app.feature_note.di

import android.app.Application
import androidx.room.Room
import com.example.mvvm_note_app.feature_note.data.NoteDatabase
import com.example.mvvm_note_app.feature_note.data.NoteRepositoryImpl
import com.example.mvvm_note_app.feature_note.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }
}