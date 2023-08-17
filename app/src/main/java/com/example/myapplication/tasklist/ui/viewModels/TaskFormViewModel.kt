package com.example.myapplication.tasklist.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.tasklist.ui.data.TaskResult

class TaskFormViewModel : ViewModel() {

    private val _taskAdded = MutableLiveData<Boolean>()
    val taskAdded: LiveData<Boolean> = _taskAdded
    lateinit var taskResult: TaskResult

    fun addTask(title: String, description: String) {
        taskResult = TaskResult(title = title, description = description)
        _taskAdded.value = true
    }

}
