package com.example.myapplication.tasklist.ui.data

import com.example.myapplication.repository.TaskDTO

sealed class TaskDetailEvents {

    data class DeleteTask(
        val taskId: String,
    ) : TaskDetailEvents()

    data class UpdateTask(
        val taskDTO: TaskDTO
    ) : TaskDetailEvents()

    object GoBack : TaskDetailEvents()

}