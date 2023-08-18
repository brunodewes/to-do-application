package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTaskFormBinding
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskFormViewModelFactory

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private lateinit var binding: FragmentTaskFormBinding
    private val viewModel: TaskFormViewModel by viewModels(
        factoryProducer = { TaskFormViewModelFactory() }
    )
    private lateinit var taskDTO: TaskDTO

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

        binding.btnAddTask.setOnClickListener {
            val taskTitle = binding.etTaskTitle.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            taskDTO = TaskDTO(title = taskTitle, description = taskDescription, id = generateRandomId())
            if (taskTitle.isNotEmpty()) {
                viewModel.addTask(taskDTO)
                findNavController().popBackStack()
            }
        }

        binding.btnCloseForm.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun generateRandomId(): String {
        val idLength = 10
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..idLength)
            .map { characters.random() }
            .joinToString("")
    }

}