package com.example.myapplication.tasklist.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.tasklist.ui.data.TaskFormEvents
import com.example.myapplication.tasklist.ui.data.TaskFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskFormView(
    onPopBackStack: () -> Unit,
    onEvent: (TaskFormEvents) -> Unit,
    uiState: TaskFormUiState
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(TaskFormEvents.AddTask)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done, contentDescription = "Add todo",
                        )
                    }
                },
            )
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
                        onEvent(TaskFormEvents.OnTitleChanged(it))
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
                        onEvent(TaskFormEvents.OnDescriptionChanged(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
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
    TaskFormView(
        onEvent = {},
        uiState = TaskFormUiState(
            title = "Title",
            description = "Description"
        ),
        onPopBackStack = {}
    )
}