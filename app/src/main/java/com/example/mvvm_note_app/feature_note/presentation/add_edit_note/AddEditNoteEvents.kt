package com.example.mvvm_note_app.feature_note.presentation.add_edit_note


import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color

sealed class AddEditNoteEvents {
    class OnTitleChange(val title: String): AddEditNoteEvents()
    class OnTitleFocus(val focusState: FocusState): AddEditNoteEvents()
    class OnContentChange(val content: String): AddEditNoteEvents()
    class OnContentFocus(val focusState: FocusState): AddEditNoteEvents()
    class OnColorChange(val color: List<Color>): AddEditNoteEvents()
    object OnSaveClicked: AddEditNoteEvents()
}
