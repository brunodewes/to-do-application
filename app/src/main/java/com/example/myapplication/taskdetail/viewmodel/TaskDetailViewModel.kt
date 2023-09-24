package com.example.myapplication.taskdetail.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.navigation.NavigationModel
import com.example.myapplication.navigation.VIEW_MODEL_ARGUMENTS
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.taskdetail.data.TaskDetailUiEvents
import com.example.myapplication.taskdetail.data.TaskDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val taskId = savedStateHandle.get<String>(VIEW_MODEL_ARGUMENTS)!!

    private val taskTitleLiveData: MutableLiveData<String> = MutableLiveData()
    private val taskDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
    val taskDetailLiveData: MediatorLiveData<TaskDetailUiState> = MediatorLiveData()

    val navigationStream: MutableLiveData<NavigationModel> = MutableLiveData()

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { tasks ->
                    val task = tasks.find { it.id == taskId }
                    task?.let { taskDTO ->
                        taskTitleLiveData.postValue(taskDTO.title)
                        taskDescriptionLiveData.postValue(taskDTO.description.orEmpty())
                    }
                }
        }
        taskDetailLiveData.apply {
            addSource(taskTitleLiveData) { taskTitle ->
                this.value = TaskDetailUiState(
                    title = taskTitle,
                    description = taskDescriptionLiveData.value.orEmpty(),
                    id = taskId
                )
            }
            addSource(taskDescriptionLiveData) { taskDescription ->
                this.value = TaskDetailUiState(
                    title = taskTitleLiveData.value!!,
                    description = taskDescription,
                    id = taskId
                )
            }
        }
    }

    fun handleUiEvents(event: TaskDetailUiEvents) {
        when (event) {
            TaskDetailUiEvents.DeleteTask -> {
                deleteTask()
                navigationStream.postValue(NavigationModel.NavigateBack)
            }

            TaskDetailUiEvents.GoBack -> {
                navigationStream.postValue(NavigationModel.NavigateBack)
            }

            is TaskDetailUiEvents.OnDescriptionChanged -> {
                taskDescriptionLiveData.postValue(event.description)
            }

            is TaskDetailUiEvents.OnTitleChanged -> {
                taskTitleLiveData.postValue(event.title)
            }

            TaskDetailUiEvents.UpdateTask -> {
                taskTitleLiveData.value?.let {
                    if (it.isNotEmpty()) {
                        updateTask(title = it, description = taskDescriptionLiveData.value)
                        navigationStream.postValue(NavigationModel.NavigateBack)
                    }
                }
            }
        }
    }

    private fun updateTask(title: String, description: String?) {
        viewModelScope.launch {
            taskRepository
                .updateTask(TaskDTO(id = taskId, title = title, description = description))
                .collect()
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            taskRepository
                .deleteTask(taskId)
                .collect()
        }
    }
}