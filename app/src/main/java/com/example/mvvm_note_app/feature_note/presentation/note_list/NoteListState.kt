package com.example.mvvm_note_app.feature_note.presentation.note_list

import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.util.NoteOrder
import com.example.mvvm_note_app.feature_note.domain.util.OrderType

//States in our note_lists screen(What could change in our note_lists screen).
data class NoteListState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
