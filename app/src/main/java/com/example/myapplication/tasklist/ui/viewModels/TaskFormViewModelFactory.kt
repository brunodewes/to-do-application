package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.TaskRepositoryImpl

class TaskFormViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskFormViewModel(
//            taskRepository = TaskRepositoryImpl()
        ) as T
    }
}