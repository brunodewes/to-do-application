package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun getTaskById(id: String): TaskDTO {
        var foundTask: TaskDTO? = null
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { currentTasks ->
                    foundTask = currentTasks.find { it.id == id }
                }
        }
        return foundTask ?: throw NoSuchElementException("Task with ID $id not found")
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskRepository
                .deleteTask(id)
                .collect()
        }
    }

}