package com.example.myapplication.tasklist.ui.data

import com.example.myapplication.repository.TaskDTO

sealed class TaskFormEvent {

    data class AddTask(
        val taskDTO: TaskDTO,
    ) : TaskFormEvent()

}