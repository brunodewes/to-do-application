package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskFormEvent
import com.example.myapplication.tasklist.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskFormViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    private val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskFormEvent) {
        when (event) {
            is TaskFormEvent.AddTask -> {
                if(event.title.isNotEmpty()) {
                    addTask(title = event.title, description = event.description)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            TaskFormEvent.GoBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is TaskFormEvent.OnDescriptionChanged -> TODO()
            is TaskFormEvent.OnTitleChanged -> TODO()
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
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
