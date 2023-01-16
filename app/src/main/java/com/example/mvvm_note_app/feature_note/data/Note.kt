package com.example.mvvm_note_app.feature_note.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvm_note_app.ui.theme.*

@Entity(tableName = "note_entity" )
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: List<Color>
) {
    companion object {
        val noteColors = listOf(
            HotPink,
            Indigo,
            Fuchsia,
            Aqua,
            Blue,
            Orange,
            Gold,
            LightSalmon,
            Chartreuse
        )
    }
}

class InvalidNoteException(message: String): Exception(message)
