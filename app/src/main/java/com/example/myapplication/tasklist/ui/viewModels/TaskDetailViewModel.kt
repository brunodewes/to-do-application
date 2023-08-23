package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskId: String,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val taskDetailLiveData: MutableLiveData<TaskDTO?> = MutableLiveData()

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { tasks ->
                    val task = tasks.find { it.id == taskId }
                    taskDetailLiveData.postValue(task)
                }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskRepository
                .deleteTask(id)
                .collect()
        }
    }
}