package com.example.myapplication.taskdetail.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.taskdetail.data.TaskDetailUiEvents
import com.example.myapplication.taskdetail.data.TaskDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailView(
    uiState: TaskDetailUiState,
    onUiEvent: (TaskDetailUiEvents) -> Unit,
) {

    Scaffold(
        topBar = {
            Box {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { onUiEvent(TaskDetailUiEvents.GoBack) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onUiEvent(TaskDetailUiEvents.UpdateTask) }) {
                            Icon(
                                imageVector = Icons.Default.Done, contentDescription = "Save changes",
                            )
                        }
                    },
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    value = uiState.title,
                    onValueChange = {
                        onUiEvent(TaskDetailUiEvents.OnTitleChanged(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                TextField(
                    value = uiState.description,
                    onValueChange = {
                        onUiEvent(TaskDetailUiEvents.OnDescriptionChanged(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onUiEvent(TaskDetailUiEvents.DeleteTask) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete todo",
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {

        },
    )
}

@Preview
@Composable
private fun TaskDetailPreview() {
    TaskDetailView(
        uiState = TaskDetailUiState(
            title = "Title",
            description = "Description",
            id = "Id"
        ),
        onUiEvent = {}
    )
}