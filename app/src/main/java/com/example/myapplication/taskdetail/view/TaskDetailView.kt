package com.example.myapplication.taskdetail.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
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
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.primary_color),
                        titleContentColor = colorResource(id = R.color.secondary_color)
                    ),
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { onUiEvent(TaskDetailUiEvents.GoBack) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = colorResource(id = R.color.secondary_color),
                                contentDescription = "Go back",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onUiEvent(TaskDetailUiEvents.DeleteTask) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = colorResource(id = R.color.secondary_color),
                                contentDescription = "Delete todo",
                            )
                        }
                        IconButton(onClick = { onUiEvent(TaskDetailUiEvents.UpdateTask) }) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                tint = colorResource(id = R.color.secondary_color),
                                contentDescription = "Save changes",
                            )
                        }
                    },
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.task_card_color),
                    thickness = 1.dp
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.primary_color))
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.title,
                        onValueChange = {
                            onUiEvent(TaskDetailUiEvents.OnTitleChanged(it))
                        },
                        placeholder = {
                            Text(
                                text = "Title",
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.primary_text_color),
                                fontSize = 30.sp
                            )
                        },
                        textStyle = TextStyle(
                            color = colorResource(id = R.color.primary_text_color),
                            fontSize = 30.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            cursorColor = colorResource(id = R.color.tertiary_color),
                            selectionColors = TextSelectionColors(
                                handleColor = colorResource(id = R.color.tertiary_color),
                                backgroundColor = colorResource(id = R.color.selection_background_color)
                            ),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        singleLine = false,
                        maxLines = 2,
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        color = colorResource(id = R.color.task_card_color),
                        thickness = 1.dp
                    )
                    TextField(
                        modifier = Modifier.fillMaxSize(),
                        value = uiState.description,
                        onValueChange = {
                            onUiEvent(TaskDetailUiEvents.OnDescriptionChanged(it))
                        },
                        placeholder = {
                            Text(
                                text = "Description",
                                color = colorResource(id = R.color.secondary_text_color),
                                fontSize = 15.sp
                            )
                        },
                        textStyle = TextStyle(
                            color = colorResource(id = R.color.secondary_text_color),
                            fontSize = 15.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            cursorColor = colorResource(id = R.color.tertiary_color),
                            selectionColors = TextSelectionColors(
                                handleColor = colorResource(id = R.color.tertiary_color),
                                backgroundColor = colorResource(id = R.color.selection_background_color)
                            ),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = false
                    )
                }
            }
        },
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