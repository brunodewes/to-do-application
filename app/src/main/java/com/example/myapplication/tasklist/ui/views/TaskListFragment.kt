package com.example.myapplication.tasklist.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.tasklist.ui.data.TaskDetailUiState
import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.tasklist.ui.data.TaskResult
import com.example.myapplication.databinding.FragmentTaskListBinding
import com.example.myapplication.repository.TaskDTO
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModel
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModelFactory

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var taskResult: TaskResult
    private val viewModel: TaskListViewModel by viewModels(
        factoryProducer = { TaskListViewModelFactory() }
    )
    private lateinit var todoAdapter: TodoAdapter
    private var isToDelete = false

    private lateinit var taskListUiState: TaskListUiState

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

        todoAdapter = TodoAdapter(viewModel)

        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(requireContext())

        viewModel.taskListLiveData.observe(viewLifecycleOwner) {
            todoAdapter.updateTaskList(it)
            taskListUiState = viewModel.setTaskList()
        }

        binding.btnAddTask.setOnClickListener {
            setFragmentResultListener(TASK_LIST_TASK_RESULT) { key, bundle ->
                taskResult = bundle.getSerializable(TASK_LIST_TASK_RESULT) as TaskResult
                viewModel.addTask(convertTaskResultToDTO(taskResult))
            }
            findNavController().navigate(R.id.navigateToTaskFormFragment)
        }

        todoAdapter.setOnItemClickListener(object: TodoAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("taskDetailUiStateKey", convertTaskListItemUiStateToDetailUiState(taskListUiState.tasks[position]))
//                bundle.putSerializable("taskId", taskListUiState.tasks[position].id)

                setFragmentResultListener(TASK_DELETE_RESULT) { key, bundle2 ->
                    isToDelete = bundle2.getSerializable(TASK_DELETE_RESULT) as Boolean
                    if(isToDelete) {
                        viewModel.deleteTask(taskListUiState.tasks[position].id)
                        isToDelete = false
                    }
                }
                findNavController().navigate(R.id.navigateToTaskDetailFragment, bundle)
            }
        })

        binding.btnDeleteDoneTasks.setOnClickListener {
            viewModel.deleteDone()
        }

    }

    fun convertTaskListItemUiStateToDetailUiState(task: TaskListItemUiState): TaskDetailUiState {
        return TaskDetailUiState(title = task.title, description = task.description, id = task.id)
    }

    fun convertTaskResultToDTO(taskResult: TaskResult): TaskDTO {
        return TaskDTO(title = taskResult.title, description = taskResult.description, id = generateRandomId())
    }

    private fun generateRandomId(): String {
        val idLength = 10
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..idLength)
            .map { characters.random() }
            .joinToString("")
    }

    companion object {
        const val TASK_LIST_TASK_RESULT = "TASK_LIST_TASK_RESULT"
        const val TASK_DELETE_RESULT = "TASK_DELETE_RESULT"
    }
}