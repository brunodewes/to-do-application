package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModel

class TaskListFragment : Fragment() {

    private val viewModel: TaskListViewModel by viewModels(
        factoryProducer = { TaskListViewModelFactory() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState = viewModel.taskListLiveData.observeAsState()
                screenState.value?.let { uiState ->
                    TaskListView(
                        uiState = uiState,
                    ) { event -> viewModel.onEvent(event) }
                }
            }
        }
    }
}