package com.example.myapplication.tasklist.ui.data

import java.io.Serializable

data class TaskDetailUiState(
    val title: String,
    val description: String,
    val id: String
): Serializable