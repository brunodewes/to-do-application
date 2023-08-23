package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentTaskDetailBinding
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModelFactory

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var taskId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskId = requireArguments().getString("taskId") as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, TaskDetailViewModelFactory(taskId))[TaskDetailViewModel::class.java]

        viewModel.taskDetailLiveData.observe(viewLifecycleOwner) { task ->
                if (task != null) {
                    binding.etTaskTitle.setText(task.title)
                }
                if (task != null) {
                    binding.etTaskDescription.setText(task.description)
                }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseTaskDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDeleteTask.setOnClickListener {
            viewModel.deleteTask(taskId)
            findNavController().popBackStack()
        }
    }
}