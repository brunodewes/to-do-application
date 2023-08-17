package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskDTO>>
    suspend fun addTask(taskDTO: TaskDTO)
    suspend fun deleteTask(id: String)
}