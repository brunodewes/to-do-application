package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
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

    fun deleteDone() {
        taskListLiveData.value?.tasks?.forEach {
            if(it.isChecked) {
                viewModelScope.launch {
                    taskRepository
                        .deleteTask(it.id)
                }
            }
        }
    }
}