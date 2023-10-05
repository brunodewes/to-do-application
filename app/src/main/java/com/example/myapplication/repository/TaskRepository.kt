package com.example.myapplication.repository

import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getAllTasks(): Observable<List<TaskDTO>>
    fun addTask(taskDTO: TaskDTO)
    fun deleteTask(id: String)
    fun updateTask(taskDTO: TaskDTO)
}