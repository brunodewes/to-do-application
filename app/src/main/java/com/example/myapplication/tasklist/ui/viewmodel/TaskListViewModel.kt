package com.example.myapplication.tasklist.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.myapplication.navigation.NavigationModel
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
import com.example.myapplication.tasklist.ui.data.TaskListUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper
) : ViewModel() {

    private val checkedTasksIdStream: BehaviorSubject<List<String>> = BehaviorSubject.createDefault(
        emptyList()
    )
    private val disposables: MutableList<Disposable> = mutableListOf()

    val navigationStream: PublishSubject<NavigationModel> = PublishSubject.create()

    fun buildUiState(): Observable<TaskListUiState> {
        return Observable.combineLatest(taskRepository.getAllTasks(), checkedTasksIdStream) { taskDTOList, checkedTasksId ->
            val tasks = taskDTOList.map { taskDTO ->
                taskItemUiStateMapper.mapToUiState(taskDTO = taskDTO, checkedTasksId = checkedTasksId)
            }
            TaskListUiState(tasks = tasks, isDeleteButtonActive = checkedTasksId.isNotEmpty())
        }
    }

    fun handleUiEvents(uiEvent: TaskListUiEvents) {
        when (uiEvent) {
            is TaskListUiEvents.OnCheckChanged -> {
                updateTaskCheckStatus(uiEvent.taskId)
            }

            TaskListUiEvents.DeleteDone -> {
                deleteDone()
            }

            TaskListUiEvents.OnAddTaskClick -> {
                navigationStream.onNext(NavigationModel.CreateTask)
            }

            is TaskListUiEvents.OnTaskClick -> {
                navigationStream.onNext(NavigationModel.TaskClick(uiEvent.taskId))
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun deleteDone() {
        checkedTasksIdStream
            .take(1)
            .subscribe { checkedTasksId ->
                checkedTasksId.forEach { id ->
                    taskRepository.deleteTask(id)
                    val newCheckedList = checkedTasksIdStream.value?.filterNot { it == id }
                    checkedTasksIdStream.onNext(newCheckedList)
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun updateTaskCheckStatus(taskId: String) {
        checkedTasksIdStream
            .take(1)
            .subscribe { checkedTasksId ->
                val mutableCheckedTasksId = checkedTasksId.toMutableList()
                if (!checkedTasksId.contains(taskId)) {
                    mutableCheckedTasksId.add(taskId)
                } else {
                    mutableCheckedTasksId.remove(taskId)
                }
                checkedTasksIdStream.onNext(mutableCheckedTasksId)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            if(!it.isDisposed) it.dispose()
        }
    }
}