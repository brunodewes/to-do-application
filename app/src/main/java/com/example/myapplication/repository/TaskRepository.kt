package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskDTO>>
    fun addTask(taskDTO: TaskDTO): Flow<List<TaskDTO>>
    fun deleteTask(id: String): Flow<List<TaskDTO>>
    fun updateTask(taskDTO: TaskDTO): Flow<List<TaskDTO>>
}