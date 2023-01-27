package com.example.mvvm_note_app.feature_note.domain.use_cases

import com.example.mvvm_note_app.feature_note.data.Note
import com.example.mvvm_note_app.feature_note.domain.use_cases.data.repository.FakeNoteRepository
import com.example.mvvm_note_app.feature_note.domain.util.NoteOrder
import com.example.mvvm_note_app.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        //now it needs our dao/database
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        /* Prepopulate our fake database so we can write
         a test to check if note is by Ascending or Descending. */
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                //Note is already ordered-we need to shuffle them
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeNoteRepository.insertNote(it)}
        }
    }


    //Naming convention is base on what the test do first and what it should return.
    //Since it return a flow and not a list, we run it in runBlocking function
    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to title
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to title
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to date
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isLessThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to date
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to color
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        //call note use_case we want to check from
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        //We check two notes next to each other and check that first notes is actually less than second note according to color
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}