package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun getTaskById(id: String): TaskDTO {
        return taskRepository.getTaskById(id)
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskRepository
                .deleteTask(id)
        }
    }

}