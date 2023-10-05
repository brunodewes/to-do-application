package com.example.myapplication.taskform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.navigation.NavigationModel
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.taskform.data.TaskFormUiEvents
import com.example.myapplication.taskform.data.TaskFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val taskTitleStream: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val taskDescriptionStream: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val disposables: MutableList<Disposable> = mutableListOf()

    val navigationStream: PublishSubject<NavigationModel> = PublishSubject.create()

    fun buildUiStateStream(): Observable<TaskFormUiState> {
        return Observable.combineLatest(
            taskTitleStream.distinctUntilChanged(),
            taskDescriptionStream.distinctUntilChanged(),
        ) { title, description ->
            TaskFormUiState(
                title = title,
                description = description,
            )
        }
    }

    fun handleUiEvents(uiEvent: TaskFormUiEvents) {
        when (uiEvent) {
            is TaskFormUiEvents.AddTask -> {
                taskTitleStream.value?.let {
                    if (it.isNotEmpty()) {
                        createTask()
                        navigationStream.onNext(NavigationModel.NavigateBack)
                    }
                }
            }
            TaskFormUiEvents.GoBack -> {
                navigationStream.onNext(NavigationModel.NavigateBack)
            }
            is TaskFormUiEvents.OnTitleChanged -> {
                taskTitleStream.onNext(uiEvent.title)
            }
            is TaskFormUiEvents.OnDescriptionChanged -> {
                taskDescriptionStream.onNext(uiEvent.description)
            }
        }
    }

    private fun createTask() {
        Observable.combineLatest(
            taskTitleStream.take(1),
            taskDescriptionStream.take(1),
            ::Pair,
        ).subscribe { (title, description) ->
            taskRepository.addTask(
                TaskDTO(
                    id = generateRandomId(),
                    title = title,
                    description = description,
                )
            )
        }.apply { disposables.add(this) }
    }

    private fun generateRandomId(): String {
        val idLength = 10
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..idLength)
            .map { characters.random() }
            .joinToString("")
    }

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            if(!it.isDisposed) it.dispose()
        }
    }
}
