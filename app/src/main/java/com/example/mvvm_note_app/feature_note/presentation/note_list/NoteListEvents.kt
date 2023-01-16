package com.example.mvvm_note_app.feature_note.presentation.note_list

import androidx.compose.foundation.layout.PaddingValues
import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.util.NoteOrder

sealed class NoteListEvents {
    data class Order(val noteOrder: NoteOrder): NoteListEvents()
    data class OnDeleteNote(val note: Note): NoteListEvents()
    object OnUndoDeleteNote: NoteListEvents()
    object ToggleOrderSection: NoteListEvents()
}