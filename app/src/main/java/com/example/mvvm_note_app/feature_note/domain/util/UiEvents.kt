package com.example.mvvm_note_app.feature_note.domain.util

sealed class UiEvents{
    data class Navigate(val route: String): UiEvents()
    data class ShowSnackBar(val message: String, val action: String?): UiEvents()
    object PopBackStack: UiEvents()
    object SaveNote: UiEvents()
}
