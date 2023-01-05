package com.example.mvvm_note_app.feature_note.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_entity")
    fun getNote(): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM note_entity WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
}