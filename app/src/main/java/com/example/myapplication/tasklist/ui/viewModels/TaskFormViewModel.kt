package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskFormViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun addTask(taskDTO: TaskDTO) {
        viewModelScope.launch {
            taskRepository
                .addTask(taskDTO)
        }
    }

}
