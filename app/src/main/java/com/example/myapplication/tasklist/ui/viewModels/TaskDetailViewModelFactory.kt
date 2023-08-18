package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.TaskRepositoryImpl

class TaskDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskDetailViewModel(
            taskRepository = TaskRepositoryImpl()
        ) as T
    }
}