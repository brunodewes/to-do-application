package com.example.myapplication.tasklist.ui.data

sealed class TaskListUiEvents {

    data class OnCheckChanged(
        val taskId: String,
    ) : TaskListUiEvents()

    data object DeleteDone: TaskListUiEvents()

    data class OnTaskClick(
        val taskId: String,
    ) : TaskListUiEvents()

    data object OnAddTaskClick: TaskListUiEvents()
}