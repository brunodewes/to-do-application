package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.ui.data.TaskResult
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskFormViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun addTask(taskDTO: TaskDTO) {
        viewModelScope.launch {
            val currentUiState = taskListLiveData.value?.tasks ?: emptyList()
            val newTaskUiState = taskItemUiStateMapper.mapToUiState(taskDTO)
            val updatedUiState = currentUiState + newTaskUiState

            _taskListLiveData.postValue(TaskListUiState(updatedUiState))
        }
    }
}
