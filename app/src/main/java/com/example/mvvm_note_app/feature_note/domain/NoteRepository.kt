package com.example.mvvm_note_app.feature_note.domain

import com.example.mvvm_note_app.feature_note.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(note: Note)

    suspend fun getNoteById(id: Int): Note?

    fun getNotes(): Flow<List<Note>>
}