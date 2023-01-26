package com.example.mvvm_note_app.feature_note.presentation.note_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_note_app.MainActivity
import com.example.mvvm_note_app.core.util.Constant.ORDER_SECTION
import com.example.mvvm_note_app.feature_note.di.AppModule
import com.example.mvvm_note_app.feature_note.domain.util.Routes
import com.example.mvvm_note_app.ui.theme.MVVM_Note_AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

/* First step to setup Hilt for test_cases is to annotate
with @HiltAndroidTest and tell the test we want to use
our TestAppModule with annotation. */
@HiltAndroidTest
@UninstallModules(AppModule::class) //It tell our test class not to use our real AppModule but the TestAppModule
class NoteListScreenTest {

    //We need to make sure we could inject dependency here with
    @get:Rule(order = 0) //start with this rule then unto the next rule with order numbering
    val hiltRule = HiltAndroidRule(this)

    //We need compose test rule to simulate swipes, make assertion etc
    //We use createAndroidComposeRule to get where the AndroidEntrypoint of the app is so we could simulate it.
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        //We want to inject dependencies
        hiltRule.inject()
        //Then, we set custom content for this specific test case that contain the stuff we actually want to test(note screen).
        composeRule.setContent {
            val navController = rememberNavController()
            MVVM_Note_AppTheme{
                NavHost(
                    navController = navController,
                    startDestination = Routes.ADD_NOTE_SCREEN
                ) {
                    composable(Routes.ADD_NOTE_SCREEN) {
                        AddNoteScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        //How UI Test works is it first need to find specific composable to do an action on or do an assertion on.

        //1-Try to find order_section but please don't find it.
        composeRule.onNodeWithTag(ORDER_SECTION).assertDoesNotExist()
        //2-Try to lick on our toggle icon to make order section visible
        composeRule.onNodeWithContentDescription("Drop_down").performClick()
        //3-Now we want to assert it perform the click function on the order section icon.
        composeRule.onNodeWithTag(ORDER_SECTION).assertIsDisplayed()
    }
}