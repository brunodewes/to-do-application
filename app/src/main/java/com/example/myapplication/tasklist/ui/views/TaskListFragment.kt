package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTaskListBinding
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModelFactory

class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TaskListViewModel by viewModels(
        factoryProducer = { TaskListViewModelFactory() }
    )
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.removeInvalidCheckedIds()

        todoAdapter = TodoAdapter(viewModel)

        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(requireContext())

        viewModel.taskListLiveData.observe(viewLifecycleOwner) {
            todoAdapter.updateTaskList(it)
        }

        viewModel.checkedTasksIdLiveData.observe(viewLifecycleOwner) {
            println("checkedTasksIdLiveData observer says: $it")
            binding.btnDeleteDoneTasks.visibility =
                if (it.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.navigateToTaskFormFragment)
        }

        todoAdapter.setOnItemClickListener(object: TodoAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val taskId = todoAdapter.getTaskIdAtPosition(position)
                val bundle = Bundle().apply {
                    putString("taskId", taskId)
                }
                findNavController().navigate(R.id.navigateToTaskDetailFragment, bundle)
            }
        })

        binding.btnDeleteDoneTasks.setOnClickListener {
            viewModel.deleteDone()
        }
    }
}