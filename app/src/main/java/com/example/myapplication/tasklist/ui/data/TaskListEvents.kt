package com.example.myapplication.tasklist.ui.data

sealed class TaskListEvents {

    data class OnCheckChanged(
        val taskId: String,
    ) : TaskListEvents()

    data object DeleteDone: TaskListEvents()

    data class OnTaskClick(
        val taskId: String,
    ) : TaskListEvents()

    data object OnAddTaskClick: TaskListEvents()
}