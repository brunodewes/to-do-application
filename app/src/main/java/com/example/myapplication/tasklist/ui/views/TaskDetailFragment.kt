package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.tasklist.ui.data.TaskDetailUiState
import com.example.myapplication.databinding.FragmentTaskDetailBinding
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModel

class TaskDetailFragment() : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var taskDetailUiState: TaskDetailUiState
    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            taskDetailUiState = it.getSerializable("taskDetailUiStateKey") as TaskDetailUiState
//           val id = it.getSerializable("taskId") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTaskTitle.text = taskDetailUiState.title
        binding.tvTaskDescription.text = taskDetailUiState.description

        binding.btnCloseTaskDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDeleteTask.setOnClickListener {
            setFragmentResult(
                TaskListFragment.TASK_DELETE_RESULT,
                bundleOf(TaskListFragment.TASK_DELETE_RESULT to true)
            )
            findNavController().popBackStack()
        }

    }


}