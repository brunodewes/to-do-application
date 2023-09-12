package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskFormEvents
import com.example.myapplication.tasklist.ui.data.TaskFormUiState
import com.example.myapplication.tasklist.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskTitleLiveData: MutableLiveData<String> = MutableLiveData()
    private val taskDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
    val taskFormLiveData: MediatorLiveData<TaskFormUiState> = MediatorLiveData()

    private val _uiEvents = Channel<UiEvents>()
    private val uiEvent = _uiEvents.receiveAsFlow()

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

    fun onEvent(event: TaskFormEvents) {
        when (event) {
            is TaskFormEvents.AddTask -> {
                taskTitleLiveData.value?.let {
                    if (it.isNotEmpty()) {
                        addTask(title = it, description = taskDescriptionLiveData.value)
                        sendUiEvent(UiEvents.PopBackStack)
                    }
                }
            }
            TaskFormEvents.GoBack -> {
                sendUiEvent(UiEvents.PopBackStack)
            }
            is TaskFormEvents.OnTitleChanged -> taskTitleLiveData.postValue(event.title)
            is TaskFormEvents.OnDescriptionChanged -> taskDescriptionLiveData.postValue(event.description)
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
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
