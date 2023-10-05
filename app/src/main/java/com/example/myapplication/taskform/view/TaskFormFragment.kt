package com.example.myapplication.taskform.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.navigation.NavigationPerformer
import com.example.myapplication.taskform.data.TaskFormUiState
import com.example.myapplication.taskform.viewmodel.TaskFormViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskFormFragment : Fragment() {
    @Inject
    lateinit var navigationPerformer: NavigationPerformer

    private val viewModel: TaskFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState = viewModel.buildUiStateStream()
                    .subscribeAsState(initial = TaskFormUiState("", ""))
                TaskFormView(
                    uiState = screenState.value,
                    onUiEvent = { uiEvent -> viewModel.handleUiEvents(uiEvent) }
                )
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationStream.subscribe {
            navigationPerformer.navigateTo(it)
        }
    }
}