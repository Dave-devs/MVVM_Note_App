package com.example.mvvm_note_app.feature_note.data

import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactsContract.CommonDataKinds.Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}