package com.example.myapplication.tasklist.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
) : ViewModel() {
    private val _taskListLiveData: MutableLiveData<TaskListUiState> = MutableLiveData()
    val taskListLiveData: LiveData<TaskListUiState> = _taskListLiveData

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .map { it.map(taskItemUiStateMapper::mapToUiState) }
                .collect {
                    _taskListLiveData.postValue(TaskListUiState(it))
                }
        }
    }

    fun setTaskList(): TaskListUiState {
        val currentUiState = taskListLiveData.value
        return currentUiState ?: TaskListUiState(emptyList())
    }

    fun addTask(taskDTO: TaskDTO) {
        viewModelScope.launch {
            val currentUiState = taskListLiveData.value?.tasks ?: emptyList()
            val newTaskUiState = taskItemUiStateMapper.mapToUiState(taskDTO)
            val updatedUiState = currentUiState + newTaskUiState

            _taskListLiveData.postValue(TaskListUiState(updatedUiState))
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {

            Log.d("TaskViewModel", "Deleting task with ID: $id")

            taskRepository.deleteTask(id)
            val currentUiState = taskListLiveData.value ?: return@launch
            val updatedTaskList = currentUiState.tasks.filterNot { it.id == id }
            _taskListLiveData.postValue(TaskListUiState(updatedTaskList))
        }
    }

    fun deleteDone() {
        taskListLiveData.value?.tasks?.forEach {
            if(it.isChecked) {
                deleteTask(it.id)
            }
        }
    }
}