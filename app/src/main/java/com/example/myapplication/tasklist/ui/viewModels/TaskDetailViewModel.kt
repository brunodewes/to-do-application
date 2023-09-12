package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val taskDetailLiveData: MutableLiveData<TaskDTO> = MutableLiveData()
    private var title = String()
    private var description = String()

//    init {
//        viewModelScope.launch {
//            taskRepository
//                .getAllTasks()
//                .collect { tasks ->
//                    val task = tasks.find { it.id == taskId }
//                    task?.let {
//                        taskDetailLiveData.postValue(it)
//                    }
//                }
//        }
//    }

    fun updateTask() {
        viewModelScope.launch {
            taskRepository
                .updateTask(TaskDTO("taskId", title, description))
                .collect()
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