package com.example.myapplication.taskdetail.data


sealed class TaskDetailUiEvents {

    data class OnTitleChanged(
        val title: String,
    ) : TaskDetailUiEvents()

    data class OnDescriptionChanged(
        val description: String,
    ) : TaskDetailUiEvents()

    data object DeleteTask : TaskDetailUiEvents()

    data object UpdateTask : TaskDetailUiEvents()

    data object GoBack : TaskDetailUiEvents()
}