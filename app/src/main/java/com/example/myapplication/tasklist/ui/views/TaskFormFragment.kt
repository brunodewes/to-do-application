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
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTaskFormBinding
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private lateinit var binding: FragmentTaskFormBinding
    private val viewModel: TaskFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.taskAdded.observe(viewLifecycleOwner) { taskAdded ->
            if (taskAdded) {
                    setFragmentResult(
                        TaskListFragment.TASK_LIST_TASK_RESULT,
                        bundleOf(TaskListFragment.TASK_LIST_TASK_RESULT to viewModel.taskResult)
                    )
                    findNavController().popBackStack()
            }
        }

        binding.btnAddTask.setOnClickListener {
            val taskTitle = binding.etTaskTitle.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            if (taskTitle.isNotEmpty()) {
                viewModel.addTask(taskTitle, taskDescription)
            }
        }

        binding.btnCloseForm.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}