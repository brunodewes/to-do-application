package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class TaskRepositoryImpl : TaskRepository {

    private val _taskDTOListStream: MutableSharedFlow<List<TaskDTO>> = MutableSharedFlow(
        replay = 1
    )

    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return _taskDTOListStream
    }

    override fun addTask(taskDTO: TaskDTO): Flow<List<TaskDTO>> {
        return _taskDTOListStream.take(1).map { currentList ->
            val updatedList = currentList + taskDTO
            println(updatedList)
            updatedList
        }
    }

    override suspend fun deleteTask(id: String) {
        val currentTasks = _taskDTOListStream.replayCache.firstOrNull() ?: emptyList()
        val updatedTasks = currentTasks.filterNot { it.id == id }
        _taskDTOListStream.emit(updatedTasks)
    }

    override fun getTaskById(id: String): TaskDTO {
        val currentTasks = _taskDTOListStream.replayCache.firstOrNull() ?: emptyList()
        return currentTasks.find { it.id == id }!!
    }
}