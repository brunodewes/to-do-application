package com.example.myapplication.tasklist.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.myapplication.navigation.NavigationPerformer
import com.example.myapplication.tasklist.ui.viewmodel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {
    @Inject
    lateinit var navigationPerformer: NavigationPerformer

    private val viewModel: TaskListViewModel by viewModels()

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
                        onUiEvent = { uiEvent -> viewModel.handleUiEvents(uiEvent) }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationStream.observe(viewLifecycleOwner) {
            navigationPerformer.navigateTo(it)
        }
    }
}