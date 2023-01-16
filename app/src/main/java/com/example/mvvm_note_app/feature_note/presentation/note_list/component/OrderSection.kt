package com.example.mvvm_note_app.feature_note.presentation.note_list.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.mvvm_note_app.feature_note.data.Note
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
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Date",
            selected = noteOrder is NoteOrder.Date,
            onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Color",
            selected = noteOrder is NoteOrder.Color,
            onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Ascending",
            selected = noteOrder.orderType is OrderType.Ascending,
            onSelect = { noteOrder.copy(OrderType.Ascending) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultRadioButton(
            text = "Descending",
            selected = noteOrder.orderType is OrderType.Descending,
            onSelect = { noteOrder.copy(OrderType.Descending) }
        )
    }
}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDelete: () -> Unit
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                       ColorUtils.blendARGB(note.color,  0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(width = cutCornerSize.toPx() + 100f, height = cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 12,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDelete,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
          Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "delete Icon"
          )
        }
    }
}


@Composable
fun DefaultRadioButton(
    text : String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
   Row(
       modifier = modifier,
       verticalAlignment = Alignment.CenterVertically
   ) {
       RadioButton(
           selected = selected,
           onClick = onSelect,
           colors = RadioButtonDefaults.colors(
               selectedColor = MaterialTheme.colorScheme.primary,
               unselectedColor = Color.LightGray
           )
       )
       Spacer(modifier = Modifier.width(8.dp))
       Text( text = text, style = MaterialTheme.typography.bodySmall)
   }
}