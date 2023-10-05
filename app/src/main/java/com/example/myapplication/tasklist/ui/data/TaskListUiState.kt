package com.example.myapplication.tasklist.ui.data

data class TaskListUiState(
    val tasks: List<TaskListItemUiState>,
    val isDeleteButtonActive: Boolean = false
)

data class TaskListItemUiState(
    val title: String,
    val description: String?,
    val isChecked: Boolean,
    val id: String
)