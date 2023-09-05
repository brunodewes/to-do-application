package com.example.myapplication.tasklist.ui.data

import com.example.myapplication.repository.TaskDTO

sealed class TaskFormEvent {

    data class OnTitleChanged(
        val title: String,
    ) : TaskFormEvent()

    data class OnDescriptionChanged(
        val description: String?,
    ) : TaskFormEvent()

    data class AddTask(
        val title: String,
        val description: String?,
    ) : TaskFormEvent()

    object GoBack : TaskFormEvent()

}