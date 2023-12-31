package com.example.myapplication.taskdetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.navigation.NavigationPerformer
import com.example.myapplication.taskdetail.viewmodel.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    @Inject
    lateinit var navigationPerformer: NavigationPerformer

    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                }
            }
        }
    }

