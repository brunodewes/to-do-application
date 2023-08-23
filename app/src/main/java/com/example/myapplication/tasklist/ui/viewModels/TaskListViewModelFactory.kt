package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.TaskRepositoryImpl
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapperImpl

class TaskListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskListViewModel(
            taskRepository = TaskRepositoryImpl,
            taskItemUiStateMapper = TaskItemUiStateMapperImpl(),
        ) as T
    }
}