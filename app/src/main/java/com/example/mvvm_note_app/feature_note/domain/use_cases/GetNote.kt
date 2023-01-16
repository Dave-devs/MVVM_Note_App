package com.example.mvvm_note_app.feature_note.domain.use_cases

import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }
}