

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}