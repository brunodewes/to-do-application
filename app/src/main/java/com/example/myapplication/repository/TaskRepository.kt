package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskDTO>>
    fun addTask(taskDTO: TaskDTO): Flow<List<TaskDTO>>
    suspend fun deleteTask(id: String)
    fun getTaskById(id: String): TaskDTO
}