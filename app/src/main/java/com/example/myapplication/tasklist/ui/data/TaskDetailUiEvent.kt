package com.example.myapplication.tasklist.ui.data

sealed class TaskDetailUiEvent {
    data class onTitleChanged(
        val taskId: String,
        val title: String
    )
    data class onDescriptionChanged(
        val taskId: String,
        val description: String
    )
}

