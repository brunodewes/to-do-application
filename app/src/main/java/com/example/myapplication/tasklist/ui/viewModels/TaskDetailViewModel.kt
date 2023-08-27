package com.example.myapplication.tasklist.ui.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskListUiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskId: String,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val taskDetailLiveData: MutableLiveData<TaskDTO> = MutableLiveData()
    private var title = String()
    private var description = String()

    override fun onCleared() {
        super.onCleared()

        GlobalScope.launch {
            taskRepository
                .updateTask(TaskDTO(taskId, title, description))
                .collect()
        }
    }

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { tasks ->
                    val task = tasks.find { it.id == taskId }
                    task?.let {
                        taskDetailLiveData.postValue(it)
                    }
                }
        }
    }

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskRepository
                .deleteTask(id)
                .collect()
        }
    }
}