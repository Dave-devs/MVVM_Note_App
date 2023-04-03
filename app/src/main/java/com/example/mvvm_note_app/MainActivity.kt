package com.example.mvvm_note_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvm_note_app.feature_note.domain.util.Routes
import com.example.mvvm_note_app.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.mvvm_note_app.feature_note.presentation.note_list.AddNoteScreen
import com.example.mvvm_note_app.ui.theme.MVVM_Note_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
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
    }
}