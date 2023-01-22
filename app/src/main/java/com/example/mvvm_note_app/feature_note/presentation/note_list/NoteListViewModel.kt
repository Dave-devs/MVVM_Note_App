package com.example.mvvm_note_app.feature_note.presentation.note_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.use_cases.NoteUseCases
import com.example.mvvm_note_app.feature_note.domain.util.NoteOrder
import com.example.mvvm_note_app.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
  private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _noteState = mutableStateOf(NoteListState())
    val noteState: State<NoteListState> = _noteState

    private var deletedNote: Note? = null

    private var getNoteJob: Job? = null

    //Initialize how the note order should be in the screen by data descending.
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(events: NoteListEvents) {
        when(events) {
            is NoteListEvents.Order -> {
                if(noteState.value.noteOrder::class == events.noteOrder::class &&
                    noteState.value.noteOrder.orderType == events.noteOrder.orderType) {
                    return
                }
                getNotes(events.noteOrder)
            }
            //We get the delete note function from deleteNote useCase class then launch it in viewModelScope.
            is NoteListEvents.OnDeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotes(events.note)
                    deletedNote = events.note
                }
            }
            is NoteListEvents.OnUndoDeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.addNotes( deletedNote ?: return@launch)
                    deletedNote = null
                }
            }
            is NoteListEvents.ToggleOrderSection -> {
                _noteState.value = noteState.value.copy(
                    isOrderSectionVisible = !noteState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _noteState.value = noteState.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}