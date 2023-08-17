package com.example.myapplication.tasklist.mapper

import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.repository.TaskDTO

class TaskItemUiStateMapperImpl : TaskItemUiStateMapper {
    override fun mapToUiState(taskDTO: TaskDTO): TaskListItemUiState {
        return TaskListItemUiState(
            title = taskDTO.title,
            description = taskDTO.description,
            id = taskDTO.id
        )
    }
}