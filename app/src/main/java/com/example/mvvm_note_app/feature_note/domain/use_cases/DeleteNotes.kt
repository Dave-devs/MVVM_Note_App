package com.example.mvvm_note_app.feature_note.domain.use_cases

import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.NoteRepository

class DeleteNotes(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}