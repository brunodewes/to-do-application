package com.example.myapplication.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class TaskRepositoryImpl : TaskRepository {

    private val taskDTOListStream: MutableSharedFlow<List<TaskDTO>> =
        MutableSharedFlow<List<TaskDTO>>(
            replay = 1
        ).apply {
            tryEmit(emptyList())
        }

    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return taskDTOListStream
    }

    override fun addTask(taskDTO: TaskDTO): Flow<List<TaskDTO>> {
        return taskDTOListStream.take(1).onEach { currentTasks ->
            val newTaskList = currentTasks + taskDTO
            taskDTOListStream.emit(newTaskList)
        }
    }

    override fun deleteTask(id: String): Flow<List<TaskDTO>> {
        return taskDTOListStream.take(1).onEach { currentTasks ->
            val newTaskList = currentTasks.filterNot { it.id == id }
            taskDTOListStream.emit(newTaskList)
        }
    }

    override fun updateTask(taskDTO: TaskDTO): Flow<List<TaskDTO>> {
        return taskDTOListStream.take(1).onEach { currentTasks ->
            currentTasks.find { it.id == taskDTO.id }?.let { foundTask ->
                foundTask.description = taskDTO.description
                if (taskDTO.title.isNotEmpty()) {
                    foundTask.title = taskDTO.title
                }
            }
            taskDTOListStream.emit(currentTasks)
        }
    }
}