package com.example.myapplication.taskdetail.data

import com.example.myapplication.repository.TaskDTO

sealed class TaskDetailEvents {

    data class DeleteTask(
        val taskId: String,
    ) : TaskDetailEvents()

    data class UpdateTask(
        val taskDTO: TaskDTO
    ) : TaskDetailEvents()

    data object GoBack : TaskDetailEvents()

}