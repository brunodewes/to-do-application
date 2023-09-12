package com.example.myapplication.tasklist.mapper

import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.repository.TaskDTO

class TaskItemUiStateMapperImpl : TaskItemUiStateMapper {
    override fun mapToUiState(taskDTO: TaskDTO, checkedTasksId: List<String>): TaskListItemUiState {
        return TaskListItemUiState(
            title = taskDTO.title,
            id = taskDTO.id,
            isChecked = checkedTasksId.contains(taskDTO.id)
        )
    }
}