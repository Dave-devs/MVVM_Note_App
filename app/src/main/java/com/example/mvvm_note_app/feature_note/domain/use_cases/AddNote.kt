package com.example.mvvm_note_app.feature_note.domain.use_cases

import com.example.mvvm_note_app.feature_note.data.InvalidNoteException
import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("title can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("contents can't be empty")
        }
        noteRepository.insertNote(note)
    }
}