package com.example.mvvm_note_app.feature_note.domain.use_cases.data.repository

import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository: NoteRepository {

    //Simulate note entity with a mutableListOf<Note>()
    private val notes = mutableListOf<Note>()


    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun insertNote(note: Note) {
       notes.add(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
       return notes.find { it.id == id }
    }

    override fun getNotes(): Flow<List<Note>> {
       return flow { emit(notes)}
    }

}