package com.example.myapplication.tasklist.ui.data

data class TaskListUiState(
    val tasks: List<TaskListItemUiState>
)

data class TaskListItemUiState(
    val title: String,
    val description: String?,
    val isChecked: Boolean,
    val id: String
)