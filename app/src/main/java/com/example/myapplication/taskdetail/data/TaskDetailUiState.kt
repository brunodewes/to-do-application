package com.example.myapplication.taskdetail.data

data class TaskDetailUiState(
    val title: String,
    val description: String,
    val id: String,
    var isEmptyTitleSnackBarActive: Boolean = false,
)