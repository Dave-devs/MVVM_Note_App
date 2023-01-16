package com.example.mvvm_note_app.feature_note.presentation.add_edit_note


data class AddEditNoteState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)