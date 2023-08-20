package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentTaskDetailBinding
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskDetailViewModelFactory

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: TaskDetailViewModel by viewModels(
        factoryProducer = { TaskDetailViewModelFactory() }
    )
    private lateinit var taskDTO: TaskDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
           val id = it.getSerializable("taskId") as String
           taskDTO = viewModel.getTaskById(id)
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

        binding.tvTaskTitle.text = taskDTO.title
        binding.tvTaskDescription.text = taskDTO.description

        binding.btnCloseTaskDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDeleteTask.setOnClickListener {
            viewModel.deleteTask(taskDTO.id)
            findNavController().popBackStack()
        }

    }


}