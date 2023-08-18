package com.example.myapplication.tasklist.ui.data

sealed class TaskListUiEvent {
    data class OnCheckChanged(
        val taskId: String,
        val isChecked: Boolean
    ) : TaskListUiEvent()
}