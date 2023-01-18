package com.example.mvvm_note_app.feature_note.domain.util

sealed class UiEvent{
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(val message: String, val action: String?): UiEvent()
    object PopBackStack: UiEvent()
    object SaveNote: UiEvent()
}
