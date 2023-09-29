package com.example.myapplication.tasklist.mapper

import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.repository.TaskDTO
import javax.inject.Inject

class TaskItemUiStateMapperImpl @Inject constructor() : TaskItemUiStateMapper {
    override fun mapToUiState(taskDTO: TaskDTO, checkedTasksId: List<String>): TaskListItemUiState {
        return TaskListItemUiState(
            title = taskDTO.title,
            description = taskDTO.description,
            id = taskDTO.id,
            isChecked = checkedTasksId.contains(taskDTO.id)
        )
    }
}