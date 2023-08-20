package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class TaskRepositoryImpl : TaskRepository {

    private val _taskDTOListStream: MutableSharedFlow<List<TaskDTO>> =
        MutableSharedFlow<List<TaskDTO>>(
            replay = 1
        ).apply {
            tryEmit(emptyList())
        }

    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return _taskDTOListStream
    }

    override fun addTask(taskDTO: TaskDTO): Flow<List<TaskDTO>> {
        return _taskDTOListStream.take(1).onEach { currentTasks ->
            val updatedTasks = currentTasks + taskDTO
            _taskDTOListStream.emit(updatedTasks)
        }
    }

    override fun deleteTask(id: String): Flow<List<TaskDTO>> {
        return _taskDTOListStream.take(1).onEach { currentTasks ->
            val updatedTasks = currentTasks.filterNot { it.id == id }
            _taskDTOListStream.emit(updatedTasks)
        }
    }
}