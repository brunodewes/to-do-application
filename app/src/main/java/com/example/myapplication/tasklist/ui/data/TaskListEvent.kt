package com.example.myapplication.tasklist.ui.data

sealed class TaskListEvent {

    data class OnCheckChanged(
        val taskId: String,
        val isChecked: Boolean,
    ) : TaskListEvent()

    object DeleteDone: TaskListEvent()

    data class OnTaskClick(
        val taskId: String,
    ) : TaskListEvent()

    object OnAddTaskClick: TaskListEvent()
}