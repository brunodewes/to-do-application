package com.example.myapplication.tasklist.ui.data

sealed class TaskFormEvents {

    data class OnTitleChanged(
        val title: String,
    ) : TaskFormEvents()

    data class OnDescriptionChanged(
        val description: String,
    ) : TaskFormEvents()

    data object AddTask : TaskFormEvents()

    data object GoBack : TaskFormEvents()

}