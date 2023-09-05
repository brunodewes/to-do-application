package com.example.myapplication.tasklist.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.tasklist.ui.data.TaskFormEvent
import com.example.myapplication.tasklist.ui.data.TaskFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskFormView(
    uiState: TaskFormUiState,
    onEvent: (TaskFormEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            Box {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(TaskFormEvent.GoBack) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onEvent(
                                TaskFormEvent.AddTask(
                                    title = uiState.title,
                                    description = uiState.description,
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Done, contentDescription = "Add todo",
                            )
                        }
                    },
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    value = uiState.title,
                    onValueChange = {
                        onEvent(TaskFormEvent.OnTitleChanged(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(40.dp))
                TextField(
                    value = uiState.description.orEmpty(),
                    onValueChange = {
                        onEvent(TaskFormEvent.OnDescriptionChanged(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                )
            }
        },
        bottomBar = {

        },
    )
}

@Preview
@Composable
private fun TaskFormPreview() {
    TaskFormView(onEvent = {})
}