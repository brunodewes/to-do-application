package com.example.myapplication.taskform.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.navigation.NavigationModel
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.taskform.data.TaskFormUiEvents
import com.example.myapplication.taskform.data.TaskFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val taskTitleLiveData: MutableLiveData<String> = MutableLiveData()
    private val taskDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
    val taskFormLiveData: MediatorLiveData<TaskFormUiState> = MediatorLiveData()

    val navigationStream: MutableLiveData<NavigationModel> = MutableLiveData()

    init {
        taskFormLiveData.value = TaskFormUiState("", "")
        taskFormLiveData.apply {
            addSource(taskTitleLiveData) { taskTitle ->
                this.value = TaskFormUiState(title = taskTitle, description = taskDescriptionLiveData.value.orEmpty())
            }
            addSource(taskDescriptionLiveData) { taskDescription ->
                this.value = TaskFormUiState(title = taskTitleLiveData.value.orEmpty(), description = taskDescription)
            }
        }
    }

    fun handleUiEvents(event: TaskFormUiEvents) {
        when (event) {
            is TaskFormUiEvents.AddTask -> {
                taskTitleLiveData.value?.let {
                    if (it.isNotEmpty()) {
                        addTask(title = it, description = taskDescriptionLiveData.value)
                        navigationStream.postValue(NavigationModel.NavigateBack)
                    }
                }
            }
            TaskFormUiEvents.GoBack -> {
                navigationStream.postValue(NavigationModel.NavigateBack)
            }
            is TaskFormUiEvents.OnTitleChanged -> {
                taskTitleLiveData.postValue(event.title)
            }
            is TaskFormUiEvents.OnDescriptionChanged -> {
                taskDescriptionLiveData.postValue(event.description)
            }
        }
    }

    private fun addTask(title: String, description: String?) {
        viewModelScope.launch {
            taskRepository
                .addTask(TaskDTO(title = title, description = description, id = generateRandomId()))
                .collect()
        }
    }

    private fun generateRandomId(): String {
        val idLength = 10
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..idLength)
            .map { characters.random() }
            .joinToString("")
    }

}
