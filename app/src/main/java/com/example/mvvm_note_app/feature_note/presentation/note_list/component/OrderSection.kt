package com.example.mvvm_note_app.feature_note.presentation.note_list.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvm_note_app.feature_note.domain.util.NoteOrder
import com.example.mvvm_note_app.feature_note.domain.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        DefaultRadioButton(
            text = "Title",
            selected = noteOrder is NoteOrder.Title,
            onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        DefaultRadioButton(
            text = "Date",
            selected = noteOrder is NoteOrder.Date,
            onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        DefaultRadioButton(
            text = "Color",
            selected = noteOrder is NoteOrder.Color,
            onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        DefaultRadioButton(
            text = "Ascending",
            selected = noteOrder.orderType is OrderType.Ascending,
            onSelect = { noteOrder.copy(OrderType.Ascending) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        DefaultRadioButton(
            text = "Descending",
            selected = noteOrder.orderType is OrderType.Descending,
            onSelect = { noteOrder.copy(OrderType.Descending) }
        )
    }
}