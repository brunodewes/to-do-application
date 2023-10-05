package com.example.myapplication.mainactivity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel(){

    val stream: BehaviorSubject<Type> = BehaviorSubject.create()

    init {
        stream.onNext(Type.AddFragment)
    }

    fun fragmentAdded() {
        stream.onNext(Type.NoOp)
    }
}