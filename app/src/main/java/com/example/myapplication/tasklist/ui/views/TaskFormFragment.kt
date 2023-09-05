package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel

class TaskFormFragment : Fragment() {

    private val viewModel: TaskFormViewModel by viewModels(
        factoryProducer = { TaskFormViewModelFactory() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TaskFormView { event -> viewModel.onEvent(event) }
            }
        }
    }
}