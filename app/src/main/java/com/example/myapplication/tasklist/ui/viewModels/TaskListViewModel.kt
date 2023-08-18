package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskListUiEvent
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
) : ViewModel() {
    private val taskListDTOLiveData: MutableLiveData<List<TaskDTO>> = MutableLiveData()
    private val checkedTasksIdLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val taskListLiveData: MediatorLiveData<TaskListUiState> = MediatorLiveData()

    init {
        taskListLiveData.apply {
            addSource(taskListDTOLiveData) { taskDTOList ->
                this.value = buildUiState(taskDTOList, checkedTasksIdLiveData.value.orEmpty())
            }
            addSource(checkedTasksIdLiveData) { checkedTasksId ->
                this.value = buildUiState(taskListDTOLiveData.value.orEmpty(), checkedTasksId)
            }
        }
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { taskListDTOLiveData.postValue(it) }
        }
    }

    private fun buildUiState(
        taskDTOList: List<TaskDTO>,
        checkedTasksId: List<String>,
    ): TaskListUiState {
        val tasks = taskDTOList.map { taskDTO ->
            taskItemUiStateMapper.mapToUiState(
                taskDTO = taskDTO,
                checkedTasksId = checkedTasksId,
            )
        }
        return TaskListUiState(tasks)
    }

    fun handleUiEvents(uiEvent: TaskListUiEvent) {
        when (uiEvent) {
            is TaskListUiEvent.OnCheckChanged -> {
                updateTaskCheckStatus(
                    taskId = uiEvent.taskId,
                    isChecked = uiEvent.isChecked,
                )
            }
        }
    }

    private fun updateTaskCheckStatus(taskId: String, isChecked: Boolean) {
        val checkedTasksId = checkedTasksIdLiveData.value.orEmpty().toMutableList()
        if (isChecked) {
            checkedTasksId.add(taskId)
        } else {
            checkedTasksId.remove(taskId)
        }
        checkedTasksIdLiveData.value = checkedTasksId
    }

    fun setUpdatedList(): TaskListUiState {
        return taskListLiveData.value ?: TaskListUiState(emptyList())
    }

    fun addTask(taskDTO: TaskDTO) {
        viewModelScope.launch {
            taskRepository.addTask(taskDTO)
        }
    }
}