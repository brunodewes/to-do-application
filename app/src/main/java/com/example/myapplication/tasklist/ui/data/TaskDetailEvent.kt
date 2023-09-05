package com.example.myapplication.tasklist.ui.data

import com.example.myapplication.repository.TaskDTO

sealed class TaskDetailEvent {

    data class DeleteTask(
        val taskId: String,
    ) : TaskDetailEvent()

    data class UpdateTask(
        val taskDTO: TaskDTO
    ) : TaskDetailEvent()

    object GoBack : TaskFormEvent()

}