package com.example.myapplication.taskform.data

sealed class TaskFormUiEvents {

    data class OnTitleChanged(
        val title: String,
    ) : TaskFormUiEvents()

    data class OnDescriptionChanged(
        val description: String,
    ) : TaskFormUiEvents()

    data object AddTask : TaskFormUiEvents()

    data object GoBack : TaskFormUiEvents()

}