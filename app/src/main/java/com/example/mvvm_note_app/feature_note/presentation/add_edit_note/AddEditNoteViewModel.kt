package com.example.mvvm_note_app.feature_note.presentation.add_edit_note

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_note_app.feature_note.data.InvalidNoteException
import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.use_cases.NoteUseCases
import com.example.mvvm_note_app.feature_note.domain.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _noteTitle = mutableStateOf(AddEditNoteState(hint = "Title"))
    val noteTitle: State<AddEditNoteState> = _noteTitle

    private val _noteContent = mutableStateOf(AddEditNoteState(hint = "Enter your note..."))
    val noteContent: State<AddEditNoteState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.shuffled())
    val noteColor: MutableState<List<Color>> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(events: AddEditNoteEvents) {
        when(events) {
            is AddEditNoteEvents.OnTitleChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = events.title
                )
            }
            is AddEditNoteEvents.OnTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !events.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvents.OnContentChange -> {
                _noteContent.value = noteContent.value.copy(
                    text = events.content
                )
            }
            is AddEditNoteEvents.OnContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !events.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvents.OnColorChange -> {
                _noteColor.value = events.color
            }
            is AddEditNoteEvents.OnSaveClicked -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNotes(
                            note = Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvents.ShowSnackBar(
                            message = e.message ?: "Unable to save note!",
                            action = null
                        ))
                    }
                }
            }
        }
    }
}