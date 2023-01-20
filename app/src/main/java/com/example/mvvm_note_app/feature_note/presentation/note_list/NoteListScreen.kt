package com.example.mvvm_note_app.feature_note.presentation.note_list

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mvvm_note_app.feature_note.domain.util.Routes
import com.example.mvvm_note_app.feature_note.presentation.note_list.component.NoteItem
import com.example.mvvm_note_app.feature_note.presentation.note_list.component.OrderSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteState = viewModel.noteState.value
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.ADD_EDIT_NOTE_SCREEN)},
                Modifier.background(color = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Note Lists",
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteListEvents.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Drop_down"
                    )
                }
            }
            AnimatedVisibility(
                visible = noteState.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    noteOrder = noteState.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteListEvents.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(noteState.notes) { note->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {navController.navigate(Routes.ADD_EDIT_NOTE_SCREEN)},
                        onDelete = {
                            viewModel.onEvent(NoteListEvents.OnDeleteNote(note))
                            scope.launch{
                                val result = snackBarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel  = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteListEvents.OnUndoDeleteNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}