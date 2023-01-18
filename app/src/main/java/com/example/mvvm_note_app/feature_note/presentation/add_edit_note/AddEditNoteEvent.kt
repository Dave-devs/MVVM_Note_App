package com.example.mvvm_note_app.feature_note.presentation.add_edit_note


import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    class OnTitleChange(val title: String): AddEditNoteEvent()
    class OnTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    class OnContentChange(val content: String): AddEditNoteEvent()
    class OnContentFocus(val focusState: FocusState): AddEditNoteEvent()
    class OnColorChange(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}