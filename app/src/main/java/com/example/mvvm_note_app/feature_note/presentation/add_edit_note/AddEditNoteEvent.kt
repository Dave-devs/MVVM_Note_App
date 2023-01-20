package com.example.mvvm_note_app.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class OnTitleChange(val title: String): AddEditNoteEvent()
    data class OnTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class OnContentChange(val content: String): AddEditNoteEvent()
    data class OnContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class OnColorChange(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}