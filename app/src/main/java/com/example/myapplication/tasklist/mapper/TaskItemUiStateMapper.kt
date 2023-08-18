package com.example.myapplication.tasklist.mapper

import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.repository.TaskDTO

interface TaskItemUiStateMapper {
    fun mapToUiState(taskDTO: TaskDTO, checkedTasksId: List<String>): TaskListItemUiState
}