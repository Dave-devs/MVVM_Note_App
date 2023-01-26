package com.example.mvvm_note_app.feature_note.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvm_note_app.MainActivity
import com.example.mvvm_note_app.core.util.Constant.CONTENT_TEXT_FIELD
import com.example.mvvm_note_app.core.util.Constant.NOTE_ITEM
import com.example.mvvm_note_app.core.util.Constant.TITLE_TEXT_FIELD
import com.example.mvvm_note_app.feature_note.di.AppModule
import com.example.mvvm_note_app.feature_note.domain.util.Routes
import com.example.mvvm_note_app.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.mvvm_note_app.feature_note.presentation.note_list.AddNoteScreen
import com.example.mvvm_note_app.ui.theme.MVVM_Note_AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//This test the actual user interaction on the app/Simulate what a end-user could do in the app.
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTesting {

    //We need to make sure we could inject dependency here with
    @get:Rule(order = 0) //start with this rule then unto the next rule with order numbering
    val hiltRule = HiltAndroidRule(this)

    //We need compose test rule to simulate swipes, make assertion etc
    //We use createAndroidComposeRule to get where the AndroidEntrypoint of the app is so we could simulate it.
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        //Then, we set custom content for all screens in the app to simulate navigation from screen to screen and simulate user-interaction.
        composeRule.setContent {
            MVVM_Note_AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.ADD_NOTE_SCREEN
                ) {
                    composable(Routes.ADD_NOTE_SCREEN) {
                        AddNoteScreen(navController = navController)
                    }
                    composable(
                        Routes.ADD_EDIT_NOTE_SCREEN + "?noteId ={noteId} & noteColor ={noteColor}",
                        arguments = listOf(
                            navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument("noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        //First click on the floatingActionButton to navigate to next screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        //Now on the AddEditNoteNoteScreen- We find title & content text-field and enter text in them
        composeRule.onNodeWithTag(TITLE_TEXT_FIELD).performTextInput("test_title")
        composeRule.onNodeWithTag(CONTENT_TEXT_FIELD).performTextInput("test_content")
        //Then we save the added note- If it works it will navigate us away from AddEditNoteScreen to NoteListScreen
        composeRule.onNodeWithContentDescription("Save").performClick()

        //We then check to see if there is any note with the note added.
        composeRule.onNodeWithText("test_title").assertIsDisplayed()
        //Now we check if we could click on the added note to navigate back to AddEditNoteScreen to edit the note.
        composeRule.onNodeWithText("test_title").performClick()

        //Now we check if our note title and content equals what we saved.
        composeRule.onNodeWithTag(TITLE_TEXT_FIELD).assertTextEquals("test_title")
        composeRule.onNodeWithTag(CONTENT_TEXT_FIELD).assertTextEquals("test_content")
        //We now edit the note
        composeRule.onNodeWithTag(TITLE_TEXT_FIELD).assertTextEquals("2")
        //Now save it again
        composeRule.onNodeWithContentDescription("Save").performClick()

        //We then check to see if there is any note with test_title 2.
        composeRule.onNodeWithText("test_title2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1 .. 3) {
            //First click on the floatingActionButton to navigate to next screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            //Now on the AddEditNoteNoteScreen- We find title & content text-field and enter text in them
            composeRule.onNodeWithTag(TITLE_TEXT_FIELD).performTextInput(i.toString())
            composeRule.onNodeWithTag(CONTENT_TEXT_FIELD).performTextInput(i.toString())
            //Then we save the added note- If it works it will navigate us away from AddEditNoteScreen to NoteListScreen
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Drop_down").performClick()

        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(NOTE_ITEM)[0].assertTextContains("3")
        composeRule.onAllNodesWithTag(NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(NOTE_ITEM)[2].assertTextContains("1")
    }
}