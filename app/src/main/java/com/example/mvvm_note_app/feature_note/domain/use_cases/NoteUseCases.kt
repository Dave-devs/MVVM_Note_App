package com.example.mvvm_note_app.feature_note.domain.use_cases

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val deleteNotes: DeleteNotes,
    val addNotes: AddNote
)
