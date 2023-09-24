package com.example.myapplication.tasklist.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.navigation.NavigationModel
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
import com.example.myapplication.tasklist.ui.data.TaskListUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper
) : ViewModel() {

    private val taskListDTOLiveData: MutableLiveData<List<TaskDTO>> = MutableLiveData()
    private val checkedTasksIdLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val taskListLiveData: MediatorLiveData<TaskListUiState> = MediatorLiveData()

    val navigationStream: MutableLiveData<NavigationModel> = MutableLiveData()

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
                .collect {
                    removeInvalidCheckedIds(it)
                    taskListDTOLiveData.postValue(it)
                }
        }
    }

    private fun buildUiState(
        taskDTOList: List<TaskDTO>,
        checkedTasksId: List<String>
    ): TaskListUiState {
        val tasks = taskDTOList.map { taskDTO ->
            taskItemUiStateMapper.mapToUiState(
                taskDTO = taskDTO,
                checkedTasksId = checkedTasksId,
            )
        }
        return TaskListUiState(tasks)
    }

    fun handleUiEvents(event: TaskListUiEvents) {
        when (event) {
            is TaskListUiEvents.OnCheckChanged -> {
                updateTaskCheckStatus(event.taskId,)
            }
            TaskListUiEvents.DeleteDone -> {
                deleteDone()
            }
            TaskListUiEvents.OnAddTaskClick -> {
                navigationStream.postValue(NavigationModel.CreateTask)
            }
            is TaskListUiEvents.OnTaskClick -> {
                navigationStream.postValue(NavigationModel.TaskClick(event.taskId))
            }
        }
    }

    private fun deleteDone() {
        viewModelScope.launch {
            checkedTasksIdLiveData.value?.forEach {
                taskRepository
                    .deleteTask(it)
                    .collect()
            }
        }
    }

    private fun updateTaskCheckStatus(taskId: String) {
        val checkedTasksId = checkedTasksIdLiveData.value.orEmpty().toMutableList()
        if (checkedTasksId.contains(taskId)) {
            checkedTasksId.remove(taskId)
        } else {
            checkedTasksId.add(taskId)
        }
        checkedTasksIdLiveData.value = checkedTasksId
        println(checkedTasksIdLiveData.value)
    }

    private fun removeInvalidCheckedIds(tasks: List<TaskDTO>) {
        val checkedIds = checkedTasksIdLiveData.value ?: emptyList()
        val taskIds = tasks.map { it.id }

        val validCheckedIds = checkedIds.filter { taskId -> taskIds.contains(taskId) }
        checkedTasksIdLiveData.postValue(validCheckedIds)
    }
}