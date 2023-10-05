package com.example.myapplication.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor() : TaskRepository {

    private val taskDTOListStream = BehaviorSubject.createDefault<List<TaskDTO>>(emptyList())

    override fun getAllTasks(): Observable<List<TaskDTO>> {
        return taskDTOListStream
    }

    override fun addTask(taskDTO: TaskDTO) {
        val newTaskList = taskDTOListStream.value?.plus(taskDTO)
        taskDTOListStream.onNext(newTaskList)
    }

    override fun deleteTask(id: String) {
        val newTaskList = taskDTOListStream.value?.filterNot { it.id == id }
        taskDTOListStream.onNext(newTaskList)
    }

    override fun updateTask(taskDTO: TaskDTO) {
        val newTaskList = taskDTOListStream.value
        newTaskList?.find { it.id == taskDTO.id }?.let { foundTask ->
            if (taskDTO.title.isNotEmpty()) {
                foundTask.title = taskDTO.title
                foundTask.description = taskDTO.description
            }
        }
        taskDTOListStream.onNext(newTaskList)
    }
}